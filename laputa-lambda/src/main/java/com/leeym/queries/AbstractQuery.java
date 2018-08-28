package com.leeym.queries;

public abstract class AbstractQuery<T> implements Query<T> {

  public abstract T process();
}
