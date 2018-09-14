package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.core.CoreService;
import com.leeym.core.Queries;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Throwables.getRootCause;
import static com.google.inject.Guice.createInjector;
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

  private final Injector injector;

  public AbstractService() {
    injector = createInjector(getModule());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Response handleRequest(final Request request, final Context context) {
    try {
      ParsedRequest parsedRequest = new ParsedRequest(request.getBody());
      Class<? extends Query> queryClass = Queries.getQuery(getAllQueries(), parsedRequest.getQ());
      Instantiator<? extends Query> instantiator = createInstantiator(queryClass, getInstantiatorModule());
      Query query = instantiator.newInstance(parsedRequest.getP());
      Type returnType = queryClass.getMethod(Query.METHOD_NAME).getGenericReturnType();
      Converter converter = createConverter(TypeLiteral.get(returnType), getInstantiatorModule());
      QueryDriver queryDriver =
        new ScopingQueryDriver(request, context,
          new MonitoringQueryDriver(
            new InjectingQueryDriver(injector)));
      Object result = queryDriver.invoke(query);
      String responseBody = converter.toString(result);
      Map<String, String> headers = ImmutableMap.<String, String>builder()
        .put("Content-Type", "text/plain")
        .put("X-Instance", this.toString())
        .put("X-Timeline", query.getChronograph().dump())
        .build();
      return new Response(SC_OK, responseBody, headers, false);
    } catch (IllegalArgumentException e) {
      return new Response(SC_BAD_REQUEST, generateResponseBody(e));
    } catch (NoSuchElementException e) {
      return new Response(SC_NOT_FOUND, generateResponseBody(e));
    } catch (Throwable e) {
      return new Response(SC_INTERNAL_SERVER_ERROR, generateResponseBody(e));
    }
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
}
