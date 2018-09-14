package com.leeym.platform.common;

import java.time.Duration;
import java.time.Instant;

public class StoppedChronograph {

  private final Class<?> scope;
  private final String eventName;
  private final Instant instant;
  private final Duration duration;

  public StoppedChronograph(RunningChronograph runningChronograph) {
    this.scope = runningChronograph.getScope();
    this.eventName = runningChronograph.getEventName();
    this.instant = runningChronograph.getInstant();
    this.duration = Duration.between(instant, Instant.now());
  }

  public Class<?> getScope() {
    return scope;
  }

  public Duration getDuration() {
    return duration;
  }

  public Instant getInstant() {
    return instant;
  }

  public String getEventName() {
    return eventName;
  }
}
