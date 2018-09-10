package com.leeym.sample;

import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.AbstractLambdaProxyHandler;
import com.leeym.platform.lambda.AbstractServiceTest;
import com.leeym.platform.lambda.Queries;
import com.leeym.platform.lambda.Query;

import java.util.Set;

public class SampleServiceTest extends AbstractServiceTest {

  @Override
  public AbstractLambdaProxyHandler getHandler() {
    return new SampleLambdaProxyHandler();
  }
}
