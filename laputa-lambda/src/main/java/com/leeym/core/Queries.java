package com.leeym.core;

import com.leeym.platform.lambda.Query;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public final class Queries {

  public static final String CORE_PACKAGE = Queries.class.getPackage().getName();

  public Set<Class<? extends Query>> getAllQueries() {
    return new Reflections("").getSubTypesOf(Query.class).stream()
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
