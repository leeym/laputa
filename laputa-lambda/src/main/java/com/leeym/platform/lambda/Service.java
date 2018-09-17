package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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
import java.util.HashSet;
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

public abstract class Service implements RequestHandler<Request, Response> {

  public abstract Set<Class<? extends Query>> getQueries();

  public abstract AbstractInstantiatorModule getInstantiatorModule();

  public abstract AbstractModule getModule();

  public abstract Package getPackage();

  @Inject
  public Chronograph chronograph;

  @Inject
  QueryExecutor queryExecutor;

  public final Injector parentInjector;

  public Service() {
    parentInjector = createInjector();
    parentInjector.injectMembers(this);
  }

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
  public Response handleRequest(final Request request, final Context context) {
    chronograph.start(this.getClass(), "handleRequest");
    Injector injector = parentInjector.createChildInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(Request.class).toInstance(request);
        bind(Context.class).toInstance(context);
      }
    });
    Response response = new Response();
    response.getHeaders().put("X-Instance", this.toString());
    response.getHeaders().put("Content-Type", "text/plain");
    getRevision().ifPresent(revision -> response.getHeaders().put("X-Revision", revision));
    try {
      RequestInterpreter interpreter = injector.getInstance(RequestInterpreter.class);
      InterpretedRequest interpretedRequest = interpreter.interpret(request.getBody());
      Class<? extends Query> queryClass = getQueryClass(interpretedRequest.getQuery());
      Instantiator<? extends Query> instantiator = chronograph.time(this.getClass(), "createInstantiator",
        () -> createInstantiator(queryClass, getInstantiatorModule()));
      Query query = instantiator.newInstance(interpretedRequest.getParameters());
      Type returnType = queryClass.getMethod(Query.METHOD_NAME).getGenericReturnType();
      Converter converter = chronograph.time(this.getClass(), "createConverter",
        () -> createConverter(TypeLiteral.get(returnType), getInstantiatorModule()));
      Object result = queryExecutor.submit(query);
      String responseBody = converter.toString(result);
      response.getHeaders().put("Content-Type", getContentType(responseBody));
      response.setStatusCode(SC_OK);
      response.setBody(responseBody);
    } catch (IllegalArgumentException e) {
      response.setStatusCode(SC_BAD_REQUEST);
      response.setBody(generateResponseBody(e));
    } catch (NoSuchElementException e) {
      response.setStatusCode(SC_NOT_FOUND);
      response.setBody(generateResponseBody(e));
    } catch (Throwable e) {
      response.setStatusCode(SC_INTERNAL_SERVER_ERROR);
      response.setBody(generateResponseBody(e));
    } finally {
      chronograph.toTimeline().ifPresent(timeline -> response.getHeaders().put("X-Timeline", timeline));
      chronograph.clear();
    }
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
    Set<Class<? extends Query>> queries = new HashSet<>();
    if (this.getClass() != CoreService.class) {
      queries.addAll(new CoreService().getQueries());
    }
    queries.addAll(getQueries());
    return queries;
  }

  private String generateResponseBody(Throwable e) {
    StringWriter sw = new StringWriter();
    getRootCause(e).printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  private String getContentType(String body) {
    if (body.startsWith("{") && body.endsWith("}")) {
      return "application/json";
    } else {
      return "text/plain";
    }
  }

  private Optional<String> getRevision() {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream("target/generated/build-metadata/build.properties"));
      return Optional.of(properties.getProperty("revision"));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

}
