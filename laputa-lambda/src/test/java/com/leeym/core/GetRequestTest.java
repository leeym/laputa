package com.leeym.core;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.leeym.platform.lambda.Service;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {

  private static final APIGatewayV2ProxyRequestEvent REQUEST = new APIGatewayV2ProxyRequestEvent();

  @BeforeClass
  public static void beforeClass() {
    Service.setRequest(REQUEST);
  }

  @Test
  public void test() {
    assertEquals(REQUEST, new GetRequest().process());
  }

}
