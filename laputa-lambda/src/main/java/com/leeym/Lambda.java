package com.leeym;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kaching.platform.converters.Converter;
import com.kaching.platform.converters.Instantiator;
import com.kaching.platform.converters.InstantiatorModule;
import com.kaching.platform.converters.Instantiators;
import com.leeym.platform.queryengine.QueryExecutorService;
import com.leeym.platform.queryengine.SimpleInstantiatorModule;
import com.leeym.platform.queryengine.SimpleQueryExecutorService;
import com.leeym.queries.Queries;
import com.leeym.queries.Query;

public class Lambda implements RequestHandler<String, String> {

  private final InstantiatorModule module = new SimpleInstantiatorModule();
  private final QueryExecutorService service = new SimpleQueryExecutorService();

  @Override
  public String handleRequest(final String str, final Context ctx) {
    try {
      Request request = new Request(str);
      Class<? extends Query> queryClass = Queries.getQuery(request.getQ());
      Instantiator<? extends Query> instantiator = Instantiators.createInstantiator(queryClass, module);
      Query query = instantiator.newInstance(request.getP());
      Class<?> returnType = queryClass.getMethod("process").getReturnType();
      Object result = service.submitAndGetResult(query);
      Converter converter = Instantiators.createConverter(returnType, module);
      return converter.toString(result);
    } catch (NoSuchMethodException e) {
      return "";
    }
  }

}
