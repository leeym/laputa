package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

public class LocalDateTimeConverterTest
  extends AbstractDualConverterTest<java.time.LocalDateTime, org.joda.time.LocalDateTime> {

  @Override
  public java.time.LocalDateTime getValue() {
    return java.time.LocalDateTime.of(2000, 1, 2, 3, 4, 5, 6000000);
  }

  @Override
  public Converter<java.time.LocalDateTime> getConverter() {
    return new LocalDateTimeConverter();
  }

  @Override
  public org.joda.time.LocalDateTime getValue2() {
    return new org.joda.time.LocalDateTime(2000, 1, 2, 3, 4, 5, 6);
  }
}
