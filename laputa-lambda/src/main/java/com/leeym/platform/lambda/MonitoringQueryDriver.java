package com.leeym.platform.lambda;

import com.leeym.platform.common.DefaultChronograph;

public class MonitoringQueryDriver implements QueryDriver {

  private final QueryDriver delegate;

  public MonitoringQueryDriver(QueryDriver delegate) {
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    query.setChronograph(new DefaultChronograph());
    return query.getChronograph().time(query.getClass(), Query.METHOD_NAME, () -> delegate.invoke(query));
  }
}
