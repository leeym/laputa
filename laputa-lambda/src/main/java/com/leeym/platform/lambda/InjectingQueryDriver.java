package com.leeym.platform.lambda;

import com.google.inject.Injector;

public class InjectingQueryDriver implements QueryDriver {

  private final Injector injector;

  public InjectingQueryDriver(Injector injector) {
    this.injector = injector;
  }

  @Override
  public <T> T invoke(final Query<T> query) {
    injector.injectMembers(query);
    return query.process();
  }
}
