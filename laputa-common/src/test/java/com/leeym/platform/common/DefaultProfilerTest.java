package com.leeym.platform.common;

import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultProfilerTest {

  @Test
  public void empty() {
    Profiler profiler = new DefaultProfiler();
    assertEquals("empty", profiler.dump());
  }

  @Test
  public void one() {
    Profiler profiler = new DefaultProfiler();
    profiler.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(profiler.dump(), containsString("t%3A0%7C1"));
  }

  @Test
  public void two() {
    Profiler profiler = new DefaultProfiler();
    profiler.time(this.getClass(), "one", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    profiler.time(this.getClass(), "two", () -> new DefaultSleeper().sleep(Duration.ofMillis(100)));
    assertThat(profiler.dump(), containsString("t%3A0%2C1"));
  }

}