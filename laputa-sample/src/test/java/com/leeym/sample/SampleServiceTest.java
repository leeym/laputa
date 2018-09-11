package com.leeym.sample;

import com.leeym.platform.lambda.AbstractService;
import com.leeym.platform.lambda.AbstractServiceTest;

public class SampleServiceTest extends AbstractServiceTest {

  public AbstractService getService() {
    return new SampleService();
  }
}
