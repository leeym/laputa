package com.leeym.platform.lambda;

public interface QueryExecutor {
  <T> T execute(Query<T> query);
}
