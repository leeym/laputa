package com.leeym.sample;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.leeym.platform.common.sleeper.DefaultSleeper;
import com.leeym.platform.common.sleeper.Sleeper;
import com.leeym.platform.converters.GsonConverter;
import com.leeym.platform.converters.LocalDateConverter;
import com.leeym.platform.converters.LocalDateTimeConverter;
import com.leeym.platform.converters.PropertiesConverter;
import com.leeym.platform.converters.ZonedDateTimeConverter;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Request;
import com.leeym.platform.lambda.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.Set;

public class SampleService extends Service {

  @Override
  public Package getPackage() {
    return SampleService.class.getPackage();
  }

  public static Set<Class<? extends Query>> queries = ImmutableSet.of(
    GetHostAddress.class,
    GetHostName.class,
    GetLocalDate.class,
    GetLocalDateTime.class,
    GetProperties.class,
    GetZonedDateTime.class,
    Sort.class,
    Uniq.class
  );

  @Override
  public Set<Class<? extends Query>> getQueries() {
    return queries;
  }

  @Override
  public AbstractInstantiatorModule getInstantiatorModule() {
    return new AbstractInstantiatorModule() {
      @Override
      protected void configure() {
        registerFor(LocalDate.class).converter(LocalDateConverter.class);
        registerFor(LocalDateTime.class).converter(LocalDateTimeConverter.class);
        registerFor(Properties.class).converter(PropertiesConverter.class);
        registerFor(Request.class).converter(new GsonConverter<>());
        registerFor(ZonedDateTime.class).converter(ZonedDateTimeConverter.class);
      }
    };
  }

  @Override
  public AbstractModule getModule() {
    return new AbstractModule() {
      @Override
      protected void configure() {
        bind(LocalDate.class).toProvider(LocalDate::now);
        bind(LocalDateTime.class).toProvider(LocalDateTime::now);
        bind(Sleeper.class).to(DefaultSleeper.class);
        bind(ZonedDateTime.class).toProvider(ZonedDateTime::now);
      }
    };
  }
}
