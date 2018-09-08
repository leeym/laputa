package com.leeym.queries;

import java.util.concurrent.Callable;

public abstract class AbstractQuery<T> implements Query<T>, Callable<T> {

  public abstract T process();

  @Override
  public T call() {
    return process();
  }
}
