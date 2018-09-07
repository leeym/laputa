package com.leeym.platform.lambda;

import com.google.inject.ImplementedBy;
import com.leeym.queries.Query;

import java.util.concurrent.Future;

@ImplementedBy(SimpleQueryExecutorService.class)
public interface QueryExecutorService {

  <T> Future<T> submit(Query<T> query);

  <T> T submitAndGetResult(Query<T> query);
}
