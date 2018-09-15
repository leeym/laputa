package com.leeym.platform.lambda;

import com.leeym.platform.common.chronograph.Chronograph;

public class MonitoringQueryDriver implements QueryDriver {

  private final Chronograph chronograph;
  private final QueryDriver delegate;

  public MonitoringQueryDriver(Chronograph chronograph, QueryDriver delegate) {
    this.chronograph = chronograph;
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    query.setChronograph(chronograph);
    return chronograph.time(query.getClass(), Query.METHOD_NAME, () -> delegate.invoke(query));
  }
}
