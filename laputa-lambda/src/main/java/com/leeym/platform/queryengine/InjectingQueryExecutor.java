package com.leeym.platform.queryengine;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.leeym.queries.Query;

public class InjectingQueryExecutor implements QueryExecutor {

  private final Injector injector = Guice.createInjector(new SimpleModule());

  @Override
  public <T> T execute(final Query<T> query) {
    injector.injectMembers(query);
    return query.process();
  }
}
