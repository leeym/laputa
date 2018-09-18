package com.leeym.core;

import com.leeym.platform.lambda.Request;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {

  private final Request request = new Request("q=GetRequest");

  @Test
  public void test() {
    assertEquals(request, getQuery().process());
  }

  GetRequest getQuery() {
    GetRequest query = new GetRequest();
    query.request = request;
    return query;
  }
}
