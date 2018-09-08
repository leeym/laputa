package com.leeym.queries;

import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Type;
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
      .map(parameter -> getTypeName(parameter.getParameterizedType()) + " " + parameter.getName())
      .collect(Collectors.joining(", ")) + ")\n";
  }

  @VisibleForTesting
  public static String getTypeName(Type type) {
    return type.getTypeName().replaceAll("java.(util|lang).", "");
  }
}
