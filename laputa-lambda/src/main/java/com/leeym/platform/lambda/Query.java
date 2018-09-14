package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.leeym.platform.common.Chronograph;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class Query<T> implements Callable<T>, Runnable {

  private Chronograph chronograph;
  private Context context;
  private Request request;

  public static final String METHOD_NAME = "process";
  public abstract T process();

  @Override
  public T call() {
    return process();
  }

  @Override
  public void run() {
    process();
  }

  public void setRequest(Request request) {
    checkNotNull(request, "request can not be null");
    checkState(this.request == null, "request exists already");
    this.request = request;
  }

  public Request getRequest() {
    checkState(request != null, "request is not set");
    return request;
  }

  public void setContext(Context context) {
    checkNotNull(context, "context can not be null");
    checkState(this.context == null, "context exists already");
    this.context = context;
  }

  public Context getContext() {
    checkState(context != null, "context is not set");
    return context;
  }

  public Chronograph getChronograph() {
    checkState(chronograph != null, "chronograph is not set");
    return chronograph;
  }

  public void setChronograph(Chronograph chronograph) {
    checkNotNull(chronograph, "chronograph can not be null");
    checkState(this.chronograph == null, "chronograph exists already");
    this.chronograph = chronograph;
  }
}
