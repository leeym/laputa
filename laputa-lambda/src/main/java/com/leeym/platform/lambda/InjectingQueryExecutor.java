package com.leeym.platform.lambda;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectingQueryExecutor implements QueryExecutor {

  private final Injector injector;

  public InjectingQueryExecutor(Module module) {
    injector = Guice.createInjector(module);
  }

  @Override
  public <T> T execute(final Query<T> query) {
    injector.injectMembers(query);
    return query.process();
  }
}
