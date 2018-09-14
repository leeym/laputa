package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;

public class ScopingQueryDriver implements QueryDriver {

  private final Request request;
  private final Context context;
  private final QueryDriver delegate;

  public ScopingQueryDriver(Request request, Context context, QueryDriver delegate) {
    this.request = request;
    this.context = context;
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    query.setRequest(request);
    query.setContext(context);
    return delegate.invoke(query);
  }

}
