package com.leeym.platform.common.chronograph;

import com.leeym.platform.common.sleeper.DefaultSleeper;
import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class DefaultChronographTest {

  @Test
  public void empty() {
    Chronograph chronograph = new DefaultChronograph();
    assertFalse(chronograph.toTimeline().isPresent());
  }

  @Test
  public void one() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.toTimeline().get(), containsString("t%3A0%7C1"));
  }

  @Test
  public void two() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    chronograph.time(this.getClass(), "two", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.toTimeline().get(), containsString("t%3A0%2C1"));
  }

}
