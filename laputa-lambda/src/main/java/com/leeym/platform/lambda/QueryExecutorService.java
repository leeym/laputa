package com.leeym.platform.lambda;

import com.google.inject.ImplementedBy;

import java.util.concurrent.Future;

@ImplementedBy(SingleThreadQueryExecutorService.class)
public interface QueryExecutorService {

  <T> Future<T> submit(Query<T> query);

  <T> T submitAndGetResult(Query<T> query);
}
