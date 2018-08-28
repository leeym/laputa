package com.leeym.platform.queryengine;

import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.leeym.platform.converters.LocalDateConverter;

import java.time.LocalDate;

public class SimpleInstantiatorModule extends AbstractInstantiatorModule {
  @Override
  protected void configure() {
    registerFor(LocalDate.class).converter(LocalDateConverter.class);
  }
}
