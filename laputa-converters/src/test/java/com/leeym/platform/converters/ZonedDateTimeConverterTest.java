package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ZonedDateTimeConverterTest
  extends AbstractDualConverterTest<java.time.ZonedDateTime, org.joda.time.DateTime> {

  @Override
  public java.time.ZonedDateTime getValue() {
    LocalDate localDate = java.time.LocalDate.of(2000, 1, 2);
    LocalTime localTime = LocalTime.of(3, 4, 5, 6000000);
    return java.time.ZonedDateTime.of(localDate, localTime, ZoneId.of("PST8PDT"));
  }

  @Override
  public Converter<java.time.ZonedDateTime> getConverter() {
    return new ZonedDateTimeConverter();
  }

  @Override
  public org.joda.time.DateTime getValue2() {
    return new org.joda.time.DateTime(2000, 1, 2, 3, 4, 5, 6, DateTimeZone.forID("PST8PDT"));
  }

  @Test
  public void test1() {
    System.out.println(getValue());
    System.out.println(getValue2());
  }
}
