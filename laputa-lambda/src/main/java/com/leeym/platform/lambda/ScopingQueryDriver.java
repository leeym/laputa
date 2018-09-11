package com.leeym.platform.lambda;

public class ScopingQueryDriver implements QueryDriver {

  private final QueryDriver delegate;

  public ScopingQueryDriver(QueryDriver delegate) {
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    return delegate.invoke(query);
  }
}
