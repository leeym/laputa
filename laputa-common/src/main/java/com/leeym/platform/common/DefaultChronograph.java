package com.leeym.platform.common;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DefaultChronograph implements Chronograph {

  private final List<Tuple4<Class<?>, String, Instant, Instant>> list;

  public DefaultChronograph() {
    this.list = new ArrayList<>();
  }

  @Override
  public <T> T time(Class<?> scope, String eventName, Callable<T> callable) {
    Instant start = Instant.now();
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      Instant stop = Instant.now();
      list.add(new Tuple4<>(scope, eventName, start, stop));
    }
  }

  @Override
  public List<Tuple4<Class<?>, String, Instant, Instant>> read() {
    return list;
  }
}
