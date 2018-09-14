package com.leeym.platform.common;

import java.time.Instant;

public class RunningChronograph {

  private final Class<?> scope;
  private final String eventName;
  private final Instant instant;

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

  public StoppedChronograph stop() {
    return new StoppedChronograph(this);
  }
}
