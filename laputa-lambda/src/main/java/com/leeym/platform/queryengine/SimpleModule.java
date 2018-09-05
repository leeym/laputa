package com.leeym.platform.queryengine;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.leeym.platform.utils.DefaultSleeper;
import com.leeym.platform.utils.Sleeper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class SimpleModule implements Module {

  @Override
  public void configure(final Binder binder) {
    binder.bind(LocalDate.class).toProvider(LocalDate::now);
    binder.bind(LocalDateTime.class).toProvider(LocalDateTime::now);
    binder.bind(Sleeper.class).to(DefaultSleeper.class);
    binder.bind(ZonedDateTime.class).toProvider(ZonedDateTime::now);
  }
}
