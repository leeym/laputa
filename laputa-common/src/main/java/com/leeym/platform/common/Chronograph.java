package com.leeym.platform.common;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Callable;

public interface Chronograph {

  <T> T time(Class<?> scope, String eventName, Callable<T> callable);

  List<Tuple4<Class<?>, String, Instant, Instant>> read();
}
