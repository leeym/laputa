package com.leeym.sample;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GetLocalDateTest {

  private static final LocalDate TODAY = LocalDate.now();

  @Test
  public void test() {
    assertEquals(TODAY, getQuery().process());
  }

  private GetLocalDate getQuery() {
    GetLocalDate query = new GetLocalDate();
    query.today = TODAY;
    return query;
  }
}
