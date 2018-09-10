package com.leeym.platform.lambda;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Queries {

  public static final String COMMON_QUERY_PACKAGE = Queries.class.getPackage().getName();

  public Set<Class<? extends Query>> getAllQueries(String prefix) {
    return Stream.of(new Reflections(COMMON_QUERY_PACKAGE), new Reflections(prefix))
      .flatMap(reflections -> reflections.getSubTypesOf(Query.class).stream())
      .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
      .collect(Collectors.toSet());
  }

  public static Class<? extends Query> getQuery(
    Set<Class<? extends Query>> queries,
    final String queryName
  ) throws NoSuchElementException {
    return queries.stream()
      .filter(aClass -> aClass.getSimpleName().equals(queryName))
      .findAny()
      .<NoSuchElementException>orElseThrow(() -> {
        throw new NoSuchElementException("Query [" + queryName + "] not found.");
      });
  }
}
