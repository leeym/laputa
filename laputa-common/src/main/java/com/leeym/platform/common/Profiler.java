package com.leeym.platform.common;

import java.util.concurrent.Callable;

public interface Profiler {

  void time(Class<?> scope, String eventName, Runnable runnable);

  <T> T time(Class<?> scope, String eventName, Callable<T> callable);

  String dump();
}