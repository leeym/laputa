package com.leeym.platform.lambda;

import com.leeym.platform.common.Profiler;

public class MonitoringQueryDriver implements QueryDriver {

  private final Profiler profiler;
  private final QueryDriver delegate;

  public MonitoringQueryDriver(Profiler profiler, QueryDriver delegate) {
    this.profiler = profiler;
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    query.setProfiler(profiler);
    return profiler.time(query.getClass(), Query.METHOD_NAME, () -> delegate.invoke(query));
  }
}
