package com.leeym.platform.queryengine;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.leeym.platform.utils.DefaultSleeper;
import com.leeym.platform.utils.Sleeper;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class SimpleModule implements Module {

  @Override
  public void configure(final Binder binder) {
    binder.bind(ZonedDateTime.class).toInstance(ZonedDateTime.now());
    binder.bind(LocalDate.class).toInstance(LocalDate.now());
    binder.bind(Sleeper.class).to(DefaultSleeper.class);
  }
}
