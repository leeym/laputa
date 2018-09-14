package com.leeym.platform.common;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class DefaultChronograph implements Chronograph {

  private final List<Tuple4<Class<?>, String, Instant, Instant>> list;

  public DefaultChronograph() {
    this.list = new ArrayList<>();
  }

  @Override
  public void time(Class<?> scope, String eventName, Runnable runnable) {
    Instant start = Instant.now();
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      Instant stop = Instant.now();
      list.add(new Tuple4<>(scope, eventName, start, stop));
    }
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
  public String dump() {
    if (list.isEmpty()) {
      return "empty";
    }
    Instant zero = list.get(0).getC();
    return "https://image-charts.com/chart?chs=999x999&cht=bhg&chd=t:"
      + list.stream().map(Tuple4::getC)
      .map(instant -> Duration.between(zero, instant))
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","))
      + "|"
      + list.stream().map(Tuple4::getD)
      .map(instant -> Duration.between(zero, instant))
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","));
  }
}
