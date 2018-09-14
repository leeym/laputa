package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.kaching.platform.common.Thunk;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.core.CoreService;
import com.leeym.platform.common.Chronograph;
import com.leeym.platform.common.DefaultChronograph;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Throwables.getRootCause;
import static com.kaching.platform.converters.Instantiators.createConverter;
import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public abstract class AbstractService implements RequestHandler<Request, Response> {

  private static final String DELIMITER = "\n        at ";

  public abstract Set<Class<? extends Query>> getQueries();

  public abstract InstantiatorModule getInstantiatorModule();

  public abstract Module getModule();

  public abstract Package getPackage();

  @SuppressWarnings("unchecked")
  @Override
  public Response handleRequest(final Request request, final Context context) {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.start(this.getClass(), "handleRequest");
    try {
      ParsedRequest parsedRequest = new ParsedRequest(request.getBody());
      Class<? extends Query> queryClass = getQueryClass(parsedRequest.getQ());
      Instantiator<? extends Query> instantiator = chronograph.time(this.getClass(), "createInstantiator",
        () -> createInstantiator(queryClass, getInstantiatorModule()));
      Query query = instantiator.newInstance(parsedRequest.getP());
      Type returnType = queryClass.getMethod(Query.METHOD_NAME).getGenericReturnType();
      Converter converter = chronograph.time(this.getClass(), "createConverter",
        () -> createConverter(TypeLiteral.get(returnType), getInstantiatorModule()));
      QueryDriver queryDriver =
        new ScopingQueryDriver(request, context,
          new MonitoringQueryDriver(chronograph,
            new InjectingQueryDriver(createInjector(chronograph))));
      Object result = queryDriver.invoke(query);
      String responseBody = converter.toString(result);
      Map<String, String> headers = new HashMap<>();
      headers.put("Content-Type", getContentType(responseBody));
      headers.put("X-Instance", this.toString());
      Response response = new Response(SC_OK, responseBody, headers, false);
      String timeline = chronograph.timeline();
      if (!timeline.isEmpty()) {
        response.getHeaders().put("X-Timeline", timeline);
      }
      return response;
    } catch (IllegalArgumentException e) {
      return new Response(SC_BAD_REQUEST, generateResponseBody(e));
    } catch (NoSuchElementException e) {
      return new Response(SC_NOT_FOUND, generateResponseBody(e));
    } catch (Throwable e) {
      return new Response(SC_INTERNAL_SERVER_ERROR, generateResponseBody(e));
    }
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
    queries.addAll(new CoreService().getQueries());
    queries.addAll(getQueries());
    return queries;
  }

  private String generateResponseBody(Throwable e) {
    Throwable r = getRootCause(e);
    return r.toString() + DELIMITER
      + Arrays.stream(getRootCause(e).getStackTrace())
      .map(StackTraceElement::toString)
      .collect(Collectors.joining(DELIMITER));
  }

  private Injector createInjector(Chronograph chronograph) {
    return new Thunk<Injector>() {
      @Override
      protected Injector compute() {
        return chronograph.time(this.getClass(), "createInjector", () -> Guice.createInjector(getModule()));
      }
    }.get();
  }

  private String getContentType(String body) {
    if (body.startsWith("{") && body.endsWith("}")) {
      return "application/json";
    } else {
      return "text/plan";
    }
  }
}
