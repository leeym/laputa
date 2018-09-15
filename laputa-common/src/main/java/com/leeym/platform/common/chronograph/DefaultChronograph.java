package com.leeym.platform.common.chronograph;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class DefaultChronograph implements Chronograph {

  private final List<RunningChronograph> runningChronographs;
  private static final String URL_BASE = "https://chart.googleapis.com/chart?";

  public DefaultChronograph() {
    this.runningChronographs = new ArrayList<>();
  }

  @Override
  public void time(Class<?> scope, String eventName, Runnable runnable) {
    RunningChronograph runningChronograph = start(scope, eventName);
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      runningChronograph.stop();
    }
  }

  @Override
  public <T> T time(Class<?> scope, String eventName, Callable<T> callable) {
    RunningChronograph runningChronograph = start(scope, eventName);
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      runningChronograph.stop();
    }
  }

  @Override
  public RunningChronograph start(Class<?> scope, String eventName) {
    RunningChronograph runningChronograph = new RunningChronograph(scope, eventName);
    runningChronographs.add(runningChronograph);
    return runningChronograph;
  }

  @Override
  public String reset() {
    runningChronographs.forEach(RunningChronograph::stop);
    List<RunningChronograph> sorted = runningChronographs.stream()
      .sorted(Comparator.comparing(RunningChronograph::getInstant))
      .filter(runningChronograph -> runningChronograph.getDuration().toMillis() > 0)
      .collect(Collectors.toList());
    if (sorted.isEmpty()) {
      return "";
    }
    Instant zero = sorted.get(0).getInstant();
    String chd = "t:"
      + sorted.stream().map(RunningChronograph::getInstant)
      .map(instant -> Duration.between(zero, instant))
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","))
      + "|"
      + sorted.stream().map(RunningChronograph::getDuration)
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","));
    StringBuffer chxl = new StringBuffer("1:");
    for (int i = 0; i < sorted.size(); i++) {
      RunningChronograph runningChronograph = sorted.get(sorted.size() - 1 - i);
      chxl.append("|").append(runningChronograph.getScope()).append("::").append(runningChronograph.getEventName());
    }
    Map<String, String> map = new HashMap<>();
    map.put("chs", "999x300");
    map.put("cht", "bhs");
    map.put("chds", "a");
    map.put("chco", "FFFFFF,000000");
    map.put("chxt", "x,y");
    map.put("chd", chd);
    map.put("chxl", chxl.toString());
    String query = map.entrySet().stream()
      .map(entry -> {
        try {
          return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.joining("&"));
    runningChronographs.clear();
    return URL_BASE + query;
  }
}
