package com.leeym.platform.lambda;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadQueryExecutorService implements QueryExecutorService {

  private final QueryExecutor queryExecutor;

  public SingleThreadQueryExecutorService(QueryExecutor queryExecutor) {
    this.queryExecutor = queryExecutor;
  }

  @Override
  public <T> Future<T> submit(final Query<T> query) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    return executorService.submit(() -> queryExecutor.execute(query));
  }

  @Override
  public <T> T submitAndGetResult(final Query<T> query) {
    try {
      return submit(query).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
