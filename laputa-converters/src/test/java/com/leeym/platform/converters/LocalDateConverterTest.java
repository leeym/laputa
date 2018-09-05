package com.leeym.platform.converters;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class LocalDateConverterTest {

  private static final LocalDateConverter converter = new LocalDateConverter();

  @Test
  public void test() {
    LocalDate today = LocalDate.now();
    String todayStr = converter.nonNullableToString(today);
    System.out.println(todayStr);
    assertEquals(today, converter.fromNonNullableString(todayStr));
  }

}