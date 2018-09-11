package com.leeym.platform.lambda;

public class MonitoringQueryDriver implements QueryDriver {

  private final QueryDriver delegate;

  public MonitoringQueryDriver(QueryDriver delegate) {
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    T t = delegate.invoke(query);
    return t;
  }
}
