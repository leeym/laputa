package com.leeym.core;

import com.google.inject.Inject;
import com.leeym.platform.common.sleeper.Sleeper;
import com.leeym.platform.lambda.Query;

import java.time.Duration;

public class Sleep extends Query<Boolean> {

  private final int seconds;
  @Inject
  Sleeper sleeper;

  public Sleep(int seconds) {
    this.seconds = seconds;
  }

  @Override
  public Boolean process() {
    sleeper.sleep(Duration.ofSeconds(seconds));
    return true;
  }
}
