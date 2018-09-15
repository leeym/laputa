package com.leeym.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetContextTest {

  @Test
  public void test() {
    assertEquals("{}", getQuery().process());
  }

  GetContext getQuery() {
    GetContext query = new GetContext();
    query.setContext(new SimpleContext());
    return query;
  }
}
