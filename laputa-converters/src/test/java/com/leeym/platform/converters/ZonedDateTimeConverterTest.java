package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

import java.time.LocalTime;
import java.time.ZoneId;

public class ZonedDateTimeConverterTest extends AbstractDualConverterTest<java.time.ZonedDateTime, org.joda.time.DateTime> {

  @Override
  public java.time.ZonedDateTime getValue() {
    return java.time.ZonedDateTime.of(java.time.LocalDate.of(2000, 1, 2), LocalTime.of(3, 4, 5, 6000000), ZoneId.systemDefault());
  }

  @Override
  public Converter<java.time.ZonedDateTime> getConverter() {
    return new ZonedDateTimeConverter();
  }

  @Override
  public org.joda.time.DateTime getValue2() {
    return new org.joda.time.DateTime(2000, 1, 2, 3, 4, 5, 6);
  }
}