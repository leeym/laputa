package com.leeym.platform.common.chronograph;

import java.time.Duration;
import java.time.Instant;

public class RunningChronograph {

  private final Class<?> scope;
  private final String eventName;
  private final Instant instant;
  private Duration duration;

  public RunningChronograph(Class<?> scope, String eventName) {
    this.scope = scope;
    this.eventName = eventName;
    this.instant = Instant.now();
  }

  public Class<?> getScope() {
    return scope;
  }

  public String getEventName() {
    return eventName;
  }

  public Instant getInstant() {
    return instant;
  }

  public Duration getDuration() {
    return duration;
  }

  public void stop() {
    if (duration == null) {
      duration = Duration.between(instant, Instant.now());
    }
  }
}
