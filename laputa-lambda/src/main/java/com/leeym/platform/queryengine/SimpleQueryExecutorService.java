package com.leeym.platform.queryengine;

import com.leeym.queries.Query;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleQueryExecutorService implements QueryExecutorService {

  @Override
  public <T> Future<T> submit(final Query<T> query) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    return executorService.submit(() -> new InjectingQueryExecutor().execute(query));
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
