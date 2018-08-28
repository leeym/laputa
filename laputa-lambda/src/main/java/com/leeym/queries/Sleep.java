package com.leeym.queries;

import com.leeym.platform.utils.Sleeper;

import javax.inject.Inject;
import java.time.Duration;

public class Sleep extends AbstractQuery<Boolean> {

  private final int seconds;
  @Inject
  Sleeper sleeper;

  public Sleep(final int seconds) {
    this.seconds = seconds;
  }

  @Override
  public Boolean process() {
    sleeper.sleep(Duration.ofSeconds(seconds));
    return true;
  }
}
