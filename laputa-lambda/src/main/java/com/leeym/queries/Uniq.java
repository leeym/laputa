package com.leeym.queries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Uniq extends AbstractQuery<Set<String>> {

  private final List<String> strings;

  public Uniq(List<String> strings) {
    this.strings = strings;
  }

  @Override
  public Set<String> process() {
    return new HashSet<>(strings);
  }
}
