package com.leeym.platform.lambda;

import com.google.inject.Inject;

public class SimpleQueryExecutor implements QueryExecutor {

  @Inject
  QueryDriver queryDriver;

  @Override
  public <T> T submit(Query<T> query) {
    return queryDriver.invoke(query);
  }
}
