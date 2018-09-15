package com.leeym.sample;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class GetZonedDateTimeTest {

  private static final ZonedDateTime NOW = ZonedDateTime.now();

  @Test
  public void test() {
    assertEquals(NOW, getQuery().process());
  }

  private GetZonedDateTime getQuery() {
    GetZonedDateTime query = new GetZonedDateTime();
    query.zonedDateTime = NOW;
    return query;
  }
}
