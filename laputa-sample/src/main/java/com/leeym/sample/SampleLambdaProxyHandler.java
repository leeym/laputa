package com.leeym.sample;

import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.AbstractLambdaProxyHandler;
import com.leeym.platform.lambda.Queries;
import com.leeym.platform.lambda.Query;

import java.util.Set;

public class SampleLambdaProxyHandler extends AbstractLambdaProxyHandler {

  @Override
  public Set<Class<? extends Query>> getAllQueries() {
    return new Queries().getAllQueries("com.leeym.sample");
  }

  @Override
  public InstantiatorModule getInstantiatorModule() {
    return new SampleInstantiatorModule();
  }

  @Override
  public Module getGuiceModule() {
    return new SampleModule();
  }
}
