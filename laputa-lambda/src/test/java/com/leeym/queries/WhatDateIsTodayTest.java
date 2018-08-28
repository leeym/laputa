package com.leeym.queries;

import com.leeym.platform.queryengine.SimpleQueryExecutorService;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class WhatDateIsTodayTest {

  @Test
  public void test() {
    assertEquals("Today is " + LocalDate.now(), getQuery().process());
  }

  private WhatDateIsToday getQuery() {
    WhatDateIsToday query = new WhatDateIsToday();
    query.service = new SimpleQueryExecutorService();
    return query;
  }
}