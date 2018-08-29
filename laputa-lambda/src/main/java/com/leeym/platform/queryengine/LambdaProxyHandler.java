package com.leeym.platform.queryengine;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.kaching.platform.converters.InstantiatorModule;
import com.kaching.platform.converters.Instantiators;
import com.leeym.queries.Queries;
import com.leeym.queries.Query;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.google.common.base.Throwables.getRootCause;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class LambdaProxyHandler implements RequestHandler<Request, Response> {

  private final InstantiatorModule module = new SimpleInstantiatorModule();
  private final QueryExecutorService service = new SimpleQueryExecutorService();

  @SuppressWarnings("unchecked")
  @Override
  public Response handleRequest(final Request request, final Context ctx) {
    try {
      ParsedRequest parsedRequest = new ParsedRequest(request.getBody());
      Class<? extends Query> queryClass = Queries.getQuery(parsedRequest.getQ());
      Instantiator<? extends Query> instantiator = Instantiators.createInstantiator(queryClass, module);
      Query query = instantiator.newInstance(parsedRequest.getP());
      Class<?> returnType = queryClass.getMethod(Query.METHOD_NAME).getReturnType();
      Converter converter = Instantiators.createConverter(returnType, module);
      Object result = service.submitAndGetResult(query);
      return new Response(SC_OK, converter.toString(result));
    } catch (IllegalArgumentException e) {
      return new Response(SC_BAD_REQUEST, generateResponseBody(e));
    } catch (NoSuchElementException e) {
      return new Response(SC_NOT_FOUND, generateResponseBody(e));
    } catch (Throwable e) {
      return new Response(SC_INTERNAL_SERVER_ERROR, generateResponseBody(e));
    }
  }

  private String generateResponseBody(Throwable e) {
    Throwable r = getRootCause(e);
    return r.toString() + "\n"
      + Arrays.stream(getRootCause(e).getStackTrace())
        .map(StackTraceElement::toString)
        .collect(Collectors.joining("\n  "));
  }
}
