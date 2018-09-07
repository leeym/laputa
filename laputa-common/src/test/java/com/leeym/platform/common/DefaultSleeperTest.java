package com.leeym.platform.common;

import org.junit.Test;

import java.time.Duration;

import static com.leeym.platform.testing.Assert.assertElapses;

public class DefaultSleeperTest {

  private static final Duration ONE_SECOND = Duration.ofSeconds(1);

  @Test
  public void test() {
    assertElapses(ONE_SECOND, () -> new DefaultSleeper().sleep(ONE_SECOND), ONE_SECOND.dividedBy(100));
  }
}
