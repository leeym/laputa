package com.leeym.platform.converters;

import com.kaching.platform.converters.Converter;

import java.time.ZonedDateTime;

public class ZonedDateTimeConverterTest extends AbstractConverterTest<ZonedDateTime> {

  @Override
  public ZonedDateTime getValue() {
    return ZonedDateTime.now();
  }

  @Override
  public Converter<ZonedDateTime> getConverter() {
    return new ZonedDateTimeConverter();
  }
}