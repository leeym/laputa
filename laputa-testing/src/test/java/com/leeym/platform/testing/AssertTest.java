package com.leeym.platform.testing;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.leeym.platform.testing.Assert.assertEquals;

public class AssertTest {

  @Test
  public void assertEquals_ZoneDateTime() {
    LocalDate localDate = LocalDate.of(2018, 12,23);
    LocalTime localTime = LocalTime.of(12, 34, 56, 789);
    ZonedDateTime eastern = ZonedDateTime.of(LocalDateTime.of(localDate, localTime), ZoneId.of("+5"));
    ZonedDateTime pacific = ZonedDateTime.of(LocalDateTime.of(localDate, localTime.plusHours(3)), ZoneId.of("+8"));
    assertEquals(eastern, pacific);
  }

}