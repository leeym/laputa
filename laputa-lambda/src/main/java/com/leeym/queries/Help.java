package com.leeym.queries;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Help extends AbstractQuery<String> {

  @Override
  public String process() {
    return Queries.getAllQueries().stream()
      .map(Help::describe)
      .sorted()
      .collect(Collectors.joining(""));
  }

  @VisibleForTesting
  public static String describe(Class<? extends Query> queryClass) {
    return queryClass.getSimpleName() + "(" + Arrays.stream(queryClass.getConstructors()[0].getParameters())
      .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
      .collect(Collectors.joining(", ")) + ")\n";
  }

}
