package com.leeym.platform.lambda;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.leeym.platform.common.chronograph.Chronograph;

public class QueryDriver {

  @Inject
  Injector injector;

  <T> T invoke(Query<T> query) {
    Chronograph chronograph = injector.getInstance(Chronograph.class);
    injector.injectMembers(query);
    return chronograph.time(query.getClass(), Query.METHOD_NAME, query::process);
  }

}
