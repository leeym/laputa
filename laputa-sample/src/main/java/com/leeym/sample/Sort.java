package com.leeym.sample;

import com.leeym.platform.lambda.Query;

import java.util.List;
import java.util.stream.Collectors;

public class Sort extends Query<List<String>> {

  private final List<String> strings;

  public Sort(List<String> strings) {
    this.strings = strings;
  }

  @Override
  public List<String> process() {
    return strings.stream().sorted().collect(Collectors.toList());
  }
}
