package com.leeym.core;

import com.google.common.annotations.VisibleForTesting;
import com.leeym.platform.lambda.Query;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Help extends Query<String> {

  @Override
  public String process() {
    return new Reflections("").getSubTypesOf(Query.class).stream()
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
