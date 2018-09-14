package com.leeym.core;

import com.google.common.collect.ImmutableMap;
import com.leeym.platform.lambda.Request;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class GetHeaderTest {

  @Test
  public void empty() {
    assertNull(getQuery(Collections.emptyMap(), "foo").process());
  }

  @Test
  public void nonEmpty() {
    assertEquals("v1", getQuery(ImmutableMap.of("k1", "v1"), "k1").process());
  }

  private GetHeader getQuery(Map<String, String> headers, String headerName) {
    GetHeader query = new GetHeader(headerName);
    query.setRequest(new Request("q=GetHeader"));
    query.getRequest().setHeaders(headers);
    return query;
  }
}