package com.leeym.platform.lambda;

import com.leeym.queries.Query;

public interface QueryExecutor {
  <T> T execute(Query<T> query);
}
