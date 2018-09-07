package com.leeym.queries;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GetLocalDateTest {

  private static final LocalDate today = LocalDate.now();

  @Test
  public void test() {
    assertEquals(today, getQuery().process());
  }

  private GetLocalDate getQuery() {
    GetLocalDate query = new GetLocalDate();
    query.today = today;
    return query;
  }
}