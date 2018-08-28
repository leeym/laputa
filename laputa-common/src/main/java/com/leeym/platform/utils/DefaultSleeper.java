package com.leeym.platform.utils;

import java.time.Duration;

public class DefaultSleeper implements Sleeper {

  @Override
  public void sleep(final Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
