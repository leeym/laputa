package com.leeym.sample;

import com.leeym.platform.lambda.AbstractService;
import com.leeym.platform.lambda.AbstractServiceTest;

public class SampleServiceTest extends AbstractServiceTest {

  @Override
  public AbstractService getHandler() {
    return new SampleService();
  }
}
