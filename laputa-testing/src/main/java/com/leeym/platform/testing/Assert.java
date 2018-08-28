package com.leeym.platform.testing;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertTrue;

public class Assert {

  public static void assertEquals(ZonedDateTime expected, ZonedDateTime actual) {
    assertTrue(expected.isEqual(actual));
  }

  public static void assertEquals(Duration expected, Duration actual, Duration delta) {
    org.junit.Assert.assertEquals(expected.toMillis(), actual.toMillis(), delta.toMillis());
  }

  public static void assertThrows(Throwable expected, Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable actual) {
      org.junit.Assert.assertEquals(expected, actual);
    }
  }

  public static void assertThrows(Throwable expectedClass, String expectedMessage, Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable actual) {
      org.junit.Assert.assertEquals(expectedClass, actual);
      org.junit.Assert.assertEquals(expectedMessage, actual.getMessage());
    }
  }

  public static void assertElapses(Duration expected, Runnable runnable, Duration delta) {
    Instant start = Instant.now();
    try {
      runnable.run();
    } catch (Throwable e) {
      // ignored
    }
    assertEquals(expected, Duration.between(start, Instant.now()), delta);
  }

  public static void assertElapses(Duration expected, Runnable runnable) {
    assertElapses(expected, runnable, Duration.ZERO);
  }

}
