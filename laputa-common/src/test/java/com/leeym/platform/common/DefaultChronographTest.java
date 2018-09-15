package com.leeym.platform.common;

import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultChronographTest {

  @Test
  public void empty() {
    Chronograph chronograph = new DefaultChronograph();
    assertEquals("", chronograph.reset());
  }

  @Test
  public void one() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.reset(), containsString("t%3A0%7C1"));
  }

  @Test
  public void two() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    chronograph.time(this.getClass(), "two", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.reset(), containsString("t%3A0%2C1"));
  }

}
