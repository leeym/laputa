package com.leeym.platform.lambda;

import com.google.inject.Injector;

public class SimpleQueryExecutor implements QueryExecutor {

  private final Injector injector;

  public SimpleQueryExecutor(Injector injector) {
    this.injector = injector;
  }

  @Override
  public <T> T submit(Query<T> query) {
    return injector.getInstance(QueryDriver.class).invoke(query);
  }
}
