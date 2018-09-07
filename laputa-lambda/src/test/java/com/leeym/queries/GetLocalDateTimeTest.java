package com.leeym.queries;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class GetLocalDateTimeTest {

  private static final LocalDateTime now = LocalDateTime.now();

  @Test
  public void test() {
    assertEquals(now, getQuery().process());
  }

  private GetLocalDateTime getQuery() {
    GetLocalDateTime query = new GetLocalDateTime();
    query.now = now;
    return query;
  }
}