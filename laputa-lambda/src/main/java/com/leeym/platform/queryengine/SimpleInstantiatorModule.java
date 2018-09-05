package com.leeym.platform.queryengine;

import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.leeym.platform.converters.LocalDateConverter;
import com.leeym.platform.converters.LocalDateTimeConverter;
import com.leeym.platform.converters.ZonedDateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class SimpleInstantiatorModule extends AbstractInstantiatorModule {
  @Override
  protected void configure() {
    registerFor(LocalDate.class).converter(LocalDateConverter.class);
    registerFor(LocalDateTime.class).converter(LocalDateTimeConverter.class);
    registerFor(ZonedDateTime.class).converter(ZonedDateTimeConverter.class);
  }
}
