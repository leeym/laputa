package com.leeym.platform.common;

import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultChronographTest {

  @Test
  public void empty() {
    Chronograph chronograph = new DefaultChronograph();
    assertEquals("empty", chronograph.dump());
  }

  @Test
  public void one() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.dump(), startsWith(DefaultChronograph.URL_BASE + "chs=1000x300&cht=bhg&chd=t:0|10"));
  }

  @Test
  public void two() {
    Chronograph chronograph = new DefaultChronograph();
    chronograph.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    chronograph.time(this.getClass(), "two", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(chronograph.dump(), startsWith(DefaultChronograph.URL_BASE + "chs=1000x300&cht=bhg&chd=t:0,10"));
  }

}