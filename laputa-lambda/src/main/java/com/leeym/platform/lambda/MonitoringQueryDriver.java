package com.leeym.platform.lambda;

import com.leeym.platform.common.DefaultChronograph;
import com.leeym.platform.common.FakeChronograph;

import static java.lang.Boolean.parseBoolean;

public class MonitoringQueryDriver implements QueryDriver {

  private final QueryDriver delegate;

  public MonitoringQueryDriver(QueryDriver delegate) {
    this.delegate = delegate;
  }

  @Override
  public <T> T invoke(Query<T> query) {
    query.setChronograph(parseBoolean(query.getRequest().getHeaders().get("X-WF-Trace"))
      ? new DefaultChronograph() : new FakeChronograph());
    return query.getChronograph().time(query.getClass(), "process", () -> delegate.invoke(query));
  }
}
