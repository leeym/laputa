package com.leeym.platform.common.chronograph;

import java.util.concurrent.Callable;

public class FakeChronograph implements Chronograph {

  @Override
  public void time(Class<?> scope, String eventName, Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T time(Class<?> scope, String eventName, Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public RunningChronograph start(Class<?> scope, String eventName) {
    return new RunningChronograph(scope, eventName);
  }

  @Override
  public String reset() {
    return "";
  }
}
