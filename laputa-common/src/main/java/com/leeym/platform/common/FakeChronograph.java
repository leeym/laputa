package com.leeym.platform.common;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class FakeChronograph implements Chronograph {

  @Override
  public <T> T time(Class<?> scope, String eventName, Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Tuple4<Class<?>, String, Instant, Instant>> read() {
    return Collections.emptyList();
  }
}
