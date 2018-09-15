package com.leeym.core;

import com.leeym.platform.common.sleeper.DefaultSleeper;
import org.junit.Test;

import java.time.Duration;

import static com.leeym.platform.testing.Assert.assertElapses;

public class SleepTest {

  @Test
  public void test() {
    assertElapses(Duration.ofSeconds(1), () -> getQuery(1).process());
  }

  private Sleep getQuery(final int seconds) {
    Sleep query = new Sleep(seconds);
    query.sleeper = new DefaultSleeper();
    return query;
  }
}
