package com.leeym.platform.lambda;

public interface QueryExecutor {

  <T> T submit(Query<T> query);
}
