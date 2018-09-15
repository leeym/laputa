package com.leeym.sample;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class GetLocalDateTimeTest {

  private static final LocalDateTime NOW = LocalDateTime.now();

  @Test
  public void test() {
    assertEquals(NOW, getQuery().process());
  }

  private GetLocalDateTime getQuery() {
    GetLocalDateTime query = new GetLocalDateTime();
    query.now = NOW;
    return query;
  }
}
