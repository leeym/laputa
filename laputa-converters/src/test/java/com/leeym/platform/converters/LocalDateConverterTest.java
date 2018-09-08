package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

import java.time.LocalDate;

public class LocalDateConverterTest extends AbstractConverterTest<LocalDate> {

  @Override
  public LocalDate getValue() {
    return LocalDate.now();
  }

  @Override
  public Converter<LocalDate> getConverter() {
    return new LocalDateConverter();
  }
}