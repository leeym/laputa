package com.leeym.platform.lambda;

import com.google.inject.Inject;
import com.leeym.platform.common.Sleeper;

import java.time.Duration;

public class Sleep extends AbstractQuery<Boolean> {

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
