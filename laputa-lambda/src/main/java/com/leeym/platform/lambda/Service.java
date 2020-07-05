package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.leeym.core.CoreService;
import com.leeym.platform.common.chronograph.Chronograph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static com.google.common.base.Throwables.getRootCause;
import static com.kaching.platform.converters.Instantiators.createConverter;
import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public abstract class Service implements RequestHandler<APIGatewayV2ProxyRequestEvent, APIGatewayV2ProxyResponseEvent> {

  private static final ThreadLocal<APIGatewayV2ProxyRequestEvent> REQUEST = new ThreadLocal<>();
  private static final ThreadLocal<Context> CONTEXT = new ThreadLocal<>();
  private final Injector injector;

  @Inject
  public Chronograph chronograph;

  @Inject
  RequestInterpreter interpreter;

  @Inject
  QueryDriver queryDriver;

  private LoadingCache<Class<? extends Query>, Instantiator<? extends Query>> instantiatorLoadingCache =
    CacheBuilder.newBuilder()
      .build(new CacheLoader<Class<? extends Query>, Instantiator<? extends Query>>() {
        @Override
        public Instantiator<? extends Query> load(Class<? extends Query> aClass) {
          return chronograph.time(this.getClass(), "createInstantiator",
            () -> createInstantiator(aClass, getInstantiatorModule()));
        }
      });
  private LoadingCache<Type, Converter> converterLoadingCache = CacheBuilder.newBuilder()
    .build(new CacheLoader<Type, Converter>() {
      @Override
      public Converter load(Type returnType) {
        return chronograph.time(this.getClass(), "createConverter",
          () -> createConverter(TypeLiteral.get(returnType), getInstantiatorModule()));
      }
    });

  public Service() {
    injector = createInjector();
    injector.injectMembers(this);
  }

  public static APIGatewayV2ProxyRequestEvent getRequest() {
    return REQUEST.get();
  }

  @VisibleForTesting
  public static void setRequest(APIGatewayV2ProxyRequestEvent request) {
    REQUEST.set(request);
  }

  public static Context getContext() {
    return CONTEXT.get();
  }

  @VisibleForTesting
  public static void setContext(Context context) {
    CONTEXT.set(context);
  }

  public abstract Set<Class<? extends Query>> getQueries();

  public abstract AbstractInstantiatorModule getInstantiatorModule();

  public abstract AbstractModule getModule();

  public abstract Package getPackage();

  public Injector createInjector() {
    return Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(new TypeLiteral<Set<Class<? extends Query>>>() {
        }).toInstance(getAllQueries());
        install(getModule());
        install(new ProfilingModule(getPackage()));
      }
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public APIGatewayV2ProxyResponseEvent handleRequest(final APIGatewayV2ProxyRequestEvent request,
                                                      final Context context) {
    chronograph.start(this.getClass(), "handleRequest");
    REQUEST.set(request);
    CONTEXT.set(context);
    APIGatewayV2ProxyResponseEvent response = chronograph.time(this.getClass(), "createResponse", () ->
      new APIGatewayV2ProxyResponseEvent() {{
        setHeaders(new HashMap<>());
        getHeaders().put("X-Instance", this.toString());
        getHeaders().put("Content-Type", "text/plain");
        getRevision().ifPresent(revision -> getHeaders().put("X-Revision", revision));
      }}
    );
    try {
      InterpretedRequest interpretedRequest = chronograph.time(this.getClass(), "interpretRequest",
        () -> interpreter.interpret(request.getBody()));
      Class<? extends Query> queryClass = chronograph.time(this.getClass(), "getQueryClass",
        () -> getQueryClass(interpretedRequest.getQuery()));
      Instantiator<? extends Query> instantiator = chronograph.time(this.getClass(), "cacheInstantiator",
        () -> instantiatorLoadingCache.get(queryClass));
      Query query = chronograph.time(this.getClass(), "instantiateQuery",
        () -> instantiator.newInstance(interpretedRequest.getParameters()));
      Type returnType = chronograph.time(this.getClass(), "getGenericReturnType",
        () -> queryClass.getMethod(Query.METHOD_NAME).getGenericReturnType());
      Converter converter = chronograph.time(this.getClass(), "cacheConverter",
        () -> converterLoadingCache.get(returnType));
      Object result = queryDriver.invoke(query);
      String responseBody = chronograph.time(this.getClass(), "convertResult",
        () -> converter.toString(result));
      chronograph.time(this.getClass(), "updateResponse",
        () -> {
          response.setStatusCode(SC_OK);
          response.setBody(responseBody);
        });
    } catch (Throwable e) {
      Throwable r = getRootCause(e);
      if (r instanceof IllegalArgumentException) {
        response.setStatusCode(SC_BAD_REQUEST);
        response.setBody(generateResponseBody(e));
      } else if (r instanceof NoSuchElementException) {
        response.setStatusCode(SC_NOT_FOUND);
        response.setBody(generateResponseBody(e));
      } else {
        response.setStatusCode(SC_INTERNAL_SERVER_ERROR);
        response.setBody(generateResponseBody(e));
      }
    }
    chronograph.toTimeline().ifPresent(timeline -> response.getHeaders().put("X-Timeline", timeline));
    chronograph.clear();
    REQUEST.remove();
    CONTEXT.remove();
    return response;
  }

  private Class<? extends Query> getQueryClass(String queryName) {
    return getAllQueries().stream()
      .filter(aClass -> aClass.getSimpleName().equals(queryName))
      .findAny()
      .<NoSuchElementException>orElseThrow(() -> {
        throw new NoSuchElementException("Query [" + queryName + "] not found.");
      });
  }

  private Set<Class<? extends Query>> getAllQueries() {
    return Sets.union(this.getQueries(), CoreService.queries);
  }

  private String generateResponseBody(Throwable e) {
    StringWriter sw = new StringWriter();
    getRootCause(e).printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  @VisibleForTesting
  public Optional<String> getRevision() {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream("target/generated/build-metadata/build.properties"));
      return Optional.of(properties.getProperty("revision"));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

}
