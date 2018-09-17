package com.leeym.platform.lambda;

import com.google.inject.ImplementedBy;

@ImplementedBy(SimpleQueryExecutor.class)
public interface QueryExecutor {

  <T> T submit(Query<T> query);
}
