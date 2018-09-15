package com.leeym.core;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Help extends Query<String> {

  @Inject
  Set<Class<? extends Query>> queries;

  @Override
  public String process() {
    return queries.stream()
      .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
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
