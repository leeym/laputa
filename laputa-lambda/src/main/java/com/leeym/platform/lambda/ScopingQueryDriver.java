package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;

public class ScopingQueryDriver implements QueryDriver {

  private final ThreadLocal<Context> context;
  private final QueryDriver delegate;

  public ScopingQueryDriver(Context context, QueryDriver delegate) {
    this.context = ThreadLocal.withInitial(() -> context);
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    return delegate.invoke(query);
  }

  public Context getContext() {
    return context.get();
  }
}
