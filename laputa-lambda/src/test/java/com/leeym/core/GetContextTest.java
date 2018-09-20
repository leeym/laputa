package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.leeym.platform.lambda.Service;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetContextTest {

  private static final Context CONTEXT = new SimpleContext();

  @BeforeClass
  public static void beforeClass() {
    Service.setContext(CONTEXT);
  }

  @Test
  public void test() {
    assertEquals(CONTEXT, new GetContext().process());
  }

}
