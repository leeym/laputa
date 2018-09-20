package com.leeym.core;

import com.leeym.platform.lambda.Request;
import com.leeym.platform.lambda.Service;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {

  private static final Request REQUEST = new Request("q=GetRequest");

  @BeforeClass
  public static void beforeClass() {
    Service.setRequest(REQUEST);
  }

  @Test
  public void test() {
    assertEquals(REQUEST, new GetRequest().process());
  }

}
