package com.leeym.platform.lambda;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QP0P1RequestInterpreter implements RequestInterpreter {

  public InterpretedRequest interpret(final String string) throws IllegalArgumentException {
    Map<String, String> map = Splitter.on('&')
      .trimResults()
      .withKeyValueSeparator("=")
      .split(string == null || string.isEmpty() ? "q=Help" : string);
    map.keySet().stream()
      .filter(s -> !s.matches("q|p\\d+"))
      .findFirst()
      .ifPresent(s -> {
        throw new IllegalArgumentException("Unknown key [" + s + "] found.");
      });
    String q = map.entrySet().stream()
      .filter(stringStringEntry -> stringStringEntry.getKey().equals("q"))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Key [q] not found."))
      .getValue();
    List<String> p = new ArrayList<>();
    for (int i = 0; map.containsKey("p" + i); i++) {
      p.add(map.get("p" + i));
    }
    return new InterpretedRequest(q, p);
  }

}
