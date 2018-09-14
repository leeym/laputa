package com.leeym.platform.common;

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
  private final List<StoppedChronograph> stoppedChronographs;
  public static final String URL_BASE = "https://chart.googleapis.com/chart?";

  public DefaultChronograph() {
    this.runningChronographs = new ArrayList<>();
    this.stoppedChronographs = new ArrayList<>();
  }

  @Override
  public void time(Class<?> scope, String eventName, Runnable runnable) {
    RunningChronograph runningChronograph = new RunningChronograph(scope, eventName);
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      stoppedChronographs.add(runningChronograph.stop());
    }
  }

  @Override
  public <T> T time(Class<?> scope, String eventName, Callable<T> callable) {
    RunningChronograph runningChronograph = new RunningChronograph(scope, eventName);
    try {
      return callable.call();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      stoppedChronographs.add(runningChronograph.stop());
    }
  }

  @Override
  public RunningChronograph start(Class<?> scope, String eventName) {
    RunningChronograph runningChronograph = new RunningChronograph(scope, eventName);
    runningChronographs.add(runningChronograph);
    return runningChronograph;
  }

  @Override
  public String timeline() {
    if (runningChronographs.isEmpty() && stoppedChronographs.isEmpty()) {
      return "";
    }
    for (RunningChronograph runningChronograph : runningChronographs) {
      stoppedChronographs.add(runningChronograph.stop());
    }
    runningChronographs.clear();
    List<StoppedChronograph> sorted = stoppedChronographs.stream()
      .sorted(Comparator.comparing(StoppedChronograph::getInstant))
      .collect(Collectors.toList());
    Instant zero = sorted.get(0).getInstant();
    String chd = "t:"
      + sorted.stream().map(StoppedChronograph::getInstant)
      .map(instant -> Duration.between(zero, instant))
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","))
      + "|"
      + sorted.stream().map(StoppedChronograph::getDuration)
      .map(Duration::toMillis)
      .map(String::valueOf)
      .collect(Collectors.joining(","));
    String chxl = "";
    for (int i = 0; i < sorted.size(); i++) {
      StoppedChronograph stoppedChronograph = sorted.get(sorted.size() - 1 - i);
      chxl = chxl + "|" + stoppedChronograph.getScope().getName() + "::" + stoppedChronograph.getEventName();
    }
    chxl = "1:" + chxl;
    Map<String, String> map = new HashMap<>();
    map.put("chs", "999x300");
    map.put("cht", "bhs");
    map.put("chds", "a");
    map.put("chco", "FFFFFF,000000");
    map.put("chxt", "x,y");
    map.put("chd", chd);
    map.put("chxl", chxl);
    String query = map.entrySet().stream()
      .map(entry -> {
        try {
          return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      })
      .collect(Collectors.joining("&"));
    stoppedChronographs.clear();
    return URL_BASE + query;
  }
}
