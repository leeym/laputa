package com.leeym.platform.converters;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class ZonedDateTimeConverterTest {

  private static final ZonedDateTimeConverter converter = new ZonedDateTimeConverter();

  @Test
  public void test() {
    ZonedDateTime now = ZonedDateTime.now();
    String nowStr = converter.nonNullableToString(now);
    System.out.println(nowStr);
    assertEquals(now, converter.fromNonNullableString(nowStr));
  }

}