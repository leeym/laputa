package com.leeym.platform.queryengine;

import com.leeym.queries.Query;

public interface QueryExecutor {
  <T> T execute(Query<T> query);
}
