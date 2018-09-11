package com.leeym.platform.lambda;

public interface QueryDriver {

  <T> T invoke(Query<T> query);
}
