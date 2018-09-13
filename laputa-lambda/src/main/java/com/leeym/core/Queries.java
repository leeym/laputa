package com.leeym.core;

import com.leeym.platform.lambda.Query;

import java.util.NoSuchElementException;
import java.util.Set;

public final class Queries {

  public static final String CORE_PACKAGE = Queries.class.getPackage().getName();

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
