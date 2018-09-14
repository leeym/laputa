package com.leeym.platform.common;

import org.junit.Test;

import java.time.Duration;

import static com.leeym.platform.testing.Assert.assertElapses;

public class FakeSleeperTest {

  @Test
  public void test() {
    assertElapses(Duration.ZERO, () -> new FakeSleeper().sleep(Duration.ofSeconds(1)));
  }

}
