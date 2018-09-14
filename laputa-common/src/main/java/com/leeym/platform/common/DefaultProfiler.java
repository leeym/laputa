package com.leeym.platform.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class DefaultProfiler implements Profiler {

  private final List<Tuple4<Class<?>, String, Instant, Duration>> list;
  public static final String URL_BASE = "https://chart.googleapis.com/chart"
    + "?chs=999x300"
    + "&cht=bhs"
    + "&chds=a"
    + "&chco=FFFFFF,000000"
    + "&chxt=x,y"
    + "&chd=";

  public DefaultProfiler() {
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
      Duration duration = Duration.between(start, stop);
      list.add(new Tuple4<>(scope, eventName, start, duration));
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
      Duration duration = Duration.between(start, stop);
      list.add(new Tuple4<>(scope, eventName, start, duration));
    }
  }

  @Override
  public String dump() {
    try {
      if (list.isEmpty()) {
        return "empty";
      }
      Instant zero = list.get(0).getC();
      final String query = "t:"
        + list.stream().map(Tuple4::getC)
        .map(instant -> Duration.between(zero, instant))
        .map(Duration::toMillis)
        .map(String::valueOf)
        .collect(Collectors.joining(","))
        + "|"
        + list.stream().map(Tuple4::getD)
        .map(Duration::toMillis)
        .map(String::valueOf)
        .collect(Collectors.joining(","));
      return URL_BASE + URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
