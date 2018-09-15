package com.leeym.core;

import com.leeym.platform.lambda.Request;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {

  @Test
  public void test() {
    assertEquals("{\"headers\":{},\"body\":\"q\\u003dGetRequest\"}", getQuery().process());
  }

  GetRequest getQuery() {
    GetRequest query = new GetRequest();
    query.setRequest(new Request("q=GetRequest"));
    return query;
  }
}
