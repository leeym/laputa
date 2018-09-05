package com.leeym.platform.testing;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Assert {

  public static void assertEquals(ZonedDateTime expected, ZonedDateTime actual) {
    assertTrue(expected.isEqual(actual));
  }

  public static void assertEquals(Duration expected, Duration actual, Duration delta) {
    org.junit.Assert.assertEquals(expected.toMillis(), actual.toMillis(), delta.toMillis());
  }

  public static void assertThrows(Class<? extends Throwable> expectedClass, Runnable runnable) {
    assertThrows(expectedClass, "", runnable);
  }

  public static void assertThrows(Class<? extends Throwable> expectedClass, String expectedMessage, Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable actual) {
      org.junit.Assert.assertEquals(expectedClass, actual.getClass());
      if (!expectedMessage.isEmpty()) {
        org.junit.Assert.assertEquals(expectedMessage, actual.getMessage());
      }
    }
  }

  public static void assertElapses(Duration expected, Runnable runnable, Duration delta) {
    Instant start = Instant.now();
    runnable.run();
    assertEquals(expected, Duration.between(start, Instant.now()), delta);
  }

  public static void assertElapses(Duration expected, Runnable runnable) {
    assertElapses(expected, runnable, Duration.ZERO);
  }

  public static void assertEmpty(Collection<?> collection) {
    assertTrue(collection.isEmpty());
  }

  public static void assertNotEmpty(Collection<?> collection) {
    assertFalse(collection.isEmpty());
  }

}
