package com.leeym.platform.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.leeym.platform.common.Profiler;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class Query<T> implements Callable<T>, Runnable {

  private Profiler profiler;
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

  public Profiler getProfiler() {
    checkState(profiler != null, "profiler is not set");
    return profiler;
  }

  public void setProfiler(Profiler profiler) {
    checkNotNull(profiler, "profiler can not be null");
    checkState(this.profiler == null, "profiler exists already");
    this.profiler = profiler;
  }
}
