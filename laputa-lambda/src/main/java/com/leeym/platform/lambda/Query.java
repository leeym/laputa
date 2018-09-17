package com.leeym.platform.lambda;

import java.util.concurrent.Callable;

public abstract class Query<T> implements Callable<T>, Runnable {

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

}
