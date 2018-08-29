package com.leeym.platform.queryengine;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.kaching.platform.converters.InstantiatorModule;
import com.kaching.platform.converters.Instantiators;
import com.leeym.queries.Queries;
import com.leeym.queries.Query;

import java.util.NoSuchElementException;

import static com.google.common.base.Throwables.getRootCause;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED;
import static org.apache.http.HttpStatus.SC_OK;

public class LambdaProxyHandler implements RequestHandler<Request, Response> {

  private final InstantiatorModule module = new SimpleInstantiatorModule();
  private final QueryExecutorService service = new SimpleQueryExecutorService();

  @SuppressWarnings("unchecked")
  @Override
  public Response handleRequest(final Request request, final Context ctx) {
    ParsedRequest parsedRequest;
    try {
      parsedRequest = new ParsedRequest(request.getBody());
    } catch (IllegalArgumentException e) {
      return new Response(SC_BAD_REQUEST, e.getMessage());
    }

    Class<? extends Query> queryClass;
    try {
      queryClass = Queries.getQuery(parsedRequest.getQ());
    } catch (NoSuchElementException e) {
      return new Response(SC_NOT_FOUND, e.getMessage());
    }

    Instantiator<? extends Query> instantiator;
    Query query;
    Class<?> returnType;
    Converter converter;
    try {
      instantiator = Instantiators.createInstantiator(queryClass, module);
      query = instantiator.newInstance(parsedRequest.getP());
      returnType = queryClass.getMethod(Query.METHOD_NAME).getReturnType();
      converter = Instantiators.createConverter(returnType, module);
    } catch (NoSuchMethodException e) {
      return new Response(SC_NOT_IMPLEMENTED, e.getMessage());
    } catch (RuntimeException e) {
      return new Response(SC_INTERNAL_SERVER_ERROR, getRootCause(e).getMessage());
    }

    try {
      Object result = service.submitAndGetResult(query);
      return new Response(SC_OK, converter.toString(result));
    } catch (IllegalArgumentException e) {
      return new Response(SC_BAD_REQUEST, e.getMessage());
    } catch (RuntimeException e) {
      return new Response(SC_INTERNAL_SERVER_ERROR, getRootCause(e).getMessage());
    }
  }

}
