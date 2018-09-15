package com.leeym.platform.testing;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static com.leeym.platform.testing.Assert.assertEmpty;
import static com.leeym.platform.testing.Assert.assertNotEmpty;
import static com.leeym.platform.testing.Assert.assertSameInstant;
import static com.leeym.platform.testing.Assert.assertThrows;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AssertTest {

  @Test
  public void assertSameInstantWhenSame() {
    LocalDate localDate = LocalDate.of(2018, 12, 23);
    LocalTime localTime = LocalTime.of(12, 34, 56);
    ZonedDateTime eastern = ZonedDateTime.of(LocalDateTime.of(localDate, localTime), ZoneId.of("+5"));
    ZonedDateTime pacific = ZonedDateTime.of(LocalDateTime.of(localDate, localTime.plusHours(3)), ZoneId.of("+8"));
    assertSameInstant(eastern, pacific);
  }

  @Test
  public void assertSameInstantWhenDifferent() {
    LocalDate localDate = LocalDate.of(2018, 12, 23);
    LocalTime localTime = LocalTime.of(12, 34, 56);
    ZonedDateTime eastern = ZonedDateTime.of(LocalDateTime.of(localDate, localTime), ZoneId.of("+5"));
    ZonedDateTime pacific = ZonedDateTime.of(LocalDateTime.of(localDate, localTime), ZoneId.of("+8"));
    try {
      assertSameInstant(eastern, pacific);
    } catch (AssertionError e) {
      assertEquals("expected:<2018-12-23T07:34:56Z> but was:<2018-12-23T04:34:56Z>", e.getMessage());
      return;
    }
    fail("It should fail");
  }

  @Test
  public void assertThrowsWhenThrow() {
    Runnable runnable = () -> {
      throw new IllegalArgumentException();
    };
    assertThrows(IllegalArgumentException.class, runnable);
  }

  @Test
  public void assertThrowsWhenNotThrow() {
    Runnable runnable = () -> {
    };
    try {
      assertThrows(IllegalArgumentException.class, runnable);
    } catch (AssertionError e) {
      assertEquals("Expected to throw java.lang.IllegalArgumentException but it does not throw", e.getMessage());
      return;
    }
    fail("It should fail");
  }

  @Test
  public void assertEmptyWhenEmpty() {
    assertEmpty(Collections.emptyList());
  }

  @Test
  public void assertEmptyWhenNotEmpty() {
    try {
      assertEmpty(Collections.singleton("A"));
    } catch (AssertionError e) {
      assertThat(e.getMessage(), containsString("Expect empty collection but found 1 element(s)"));
    }
  }

  @Test
  public void assertNotEmptyWhenNotEmpty() {
    assertNotEmpty(Collections.singleton("A"));
  }

  @Test
  public void assertNotEmptyWhenEmpty() {
    try {
      assertNotEmpty(Collections.emptySet());
    } catch (AssertionError e) {
      assertEquals("Values should be different. Actual: 0", e.getMessage());
      return;
    }
    fail("It should fail");
  }

}
