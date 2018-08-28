package com.leeym;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {

  private final String q;
  private final List<String> p;

  public Request(final String string) throws IllegalArgumentException {
    Map<String, String> map = Splitter.on('&')
      .trimResults()
      .withKeyValueSeparator("=")
      .split(string.isEmpty() ? "q=Help" : string);
    map.keySet().stream()
      .filter(s -> !s.matches("q|p\\d+"))
      .findFirst()
      .ifPresent(s -> {
        throw new IllegalArgumentException("Unknown key [" + s + "] found.");
      });
    q = map.entrySet().stream()
      .filter(stringStringEntry -> stringStringEntry.getKey().equals("q"))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Key [q] not found."))
      .getValue();
    p = new ArrayList<>();
    for (int i = 0; map.containsKey("p" + i); i++) {
      p.add(map.get("p" + i));
    }
  }

  public String getQ() {
    return q;
  }

  public List<String> getP() {
    return p;
  }

}
