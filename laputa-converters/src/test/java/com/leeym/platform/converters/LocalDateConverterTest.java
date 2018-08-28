package com.leeym.platform.converters;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class LocalDateConverterTest {

  private static final LocalDateConverter converter = new LocalDateConverter();

  @Test
  public void test() {
    for (LocalDate today = LocalDate.of(1900, 1, 1); today.isBefore(LocalDate.of(2100, 12, 31)); today = today.plusDays(1)) {
      assertEquals(today, converter.fromNonNullableString(converter.nonNullableToString(today)));
    }
  }

}