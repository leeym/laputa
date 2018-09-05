package com.leeym.platform.converters;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LocalDateTimeConverterTest {

  private static final LocalDateTimeConverter converter = new LocalDateTimeConverter();

  @Test
  public void test() {
    LocalDateTime now = LocalDateTime.now();
    String nowStr = converter.nonNullableToString(now);
    System.out.println(nowStr);
    assertEquals(now, converter.fromNonNullableString(nowStr));
  }

}