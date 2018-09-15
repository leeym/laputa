package com.leeym.sample;

import com.leeym.platform.lambda.Service;
import com.leeym.platform.lambda.ServiceTest;

public class SampleServiceTest extends ServiceTest {

  public Service getService() {
    return new SampleService();
  }
}
