package com.leeym.platform.common.chronograph;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface Chronograph {

  void time(Class<?> scope, String eventName, Runnable runnable);

  <T> T time(Class<?> scope, String eventName, Callable<T> callable);

  RunningChronograph start(Class<?> scope, String eventName);

  Optional<String> toTimeline();

  void clear();
}
