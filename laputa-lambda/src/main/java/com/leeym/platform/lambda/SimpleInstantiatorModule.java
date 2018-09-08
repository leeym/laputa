package com.leeym.platform.lambda;

import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.leeym.platform.converters.LocalDateConverter;
import com.leeym.platform.converters.LocalDateTimeConverter;
import com.leeym.platform.converters.PropertiesConverter;
import com.leeym.platform.converters.ZonedDateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Properties;

public class SimpleInstantiatorModule extends AbstractInstantiatorModule {
  @Override
  protected void configure() {
    registerFor(LocalDate.class).converter(LocalDateConverter.class);
    registerFor(LocalDateTime.class).converter(LocalDateTimeConverter.class);
    registerFor(Properties.class).converter(PropertiesConverter.class);
    registerFor(ZonedDateTime.class).converter(ZonedDateTimeConverter.class);
  }
}
