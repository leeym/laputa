package com.leeym.sample;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.leeym.platform.common.sleeper.DefaultSleeper;
import com.leeym.platform.common.sleeper.Sleeper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class SampleModule implements Module {

  @Override
  public void configure(final Binder binder) {
    binder.bind(LocalDate.class).toProvider(LocalDate::now);
    binder.bind(LocalDateTime.class).toProvider(LocalDateTime::now);
    binder.bind(Sleeper.class).to(DefaultSleeper.class);
    binder.bind(ZonedDateTime.class).toProvider(ZonedDateTime::now);
  }
}
