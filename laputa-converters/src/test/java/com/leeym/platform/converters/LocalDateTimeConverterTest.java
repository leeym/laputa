package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

import java.time.LocalDateTime;

public class LocalDateTimeConverterTest extends AbstractConverterTest<LocalDateTime> {

  @Override
  public LocalDateTime getValue() {
    return LocalDateTime.now();
  }

  @Override
  public Converter<LocalDateTime> getConverter() {
    return new LocalDateTimeConverter();
  }
}