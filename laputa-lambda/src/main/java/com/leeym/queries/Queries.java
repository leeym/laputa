package com.leeym.queries;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public final class Queries {

  private static Set<Class<? extends Query>> getAllQueries() {
    return new Reflections("com.leeym").getSubTypesOf(Query.class).stream()
            .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
            .collect(Collectors.toSet());
  }

  public static Class<? extends Query> getQuery(final String queryName) throws NoSuchElementException {
    return getAllQueries().stream()
            .filter(aClass -> aClass.getSimpleName().equals(queryName))
            .findAny()
            .orElseThrow(() -> {
              throw new NoSuchElementException("Query [" + queryName + "] not found.");
            });
  }
}
