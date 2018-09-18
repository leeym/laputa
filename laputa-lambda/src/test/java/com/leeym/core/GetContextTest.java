package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetContextTest {

  private final Context context = new SimpleContext();

  @Test
  public void test() {
    assertEquals(context, getQuery().process());
  }

  GetContext getQuery() {
    GetContext query = new GetContext();
    query.context = context;
    return query;
  }
}
