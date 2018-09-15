package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

public class LocalDateConverterTest extends AbstractDualConverterTest<java.time.LocalDate, org.joda.time.LocalDate> {

  @Override
  public java.time.LocalDate getValue() {
    return java.time.LocalDate.now();
  }

  @Override
  public Converter<java.time.LocalDate> getConverter() {
    return new LocalDateConverter();
  }

  @Override
  public org.joda.time.LocalDate getValue2() {
    return org.joda.time.LocalDate.now();
  }

}
