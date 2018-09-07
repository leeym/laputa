package com.leeym.platform.testing;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class Assert {

  public static void assertSameInstant(ZonedDateTime expected, ZonedDateTime actual) {
    assertEquals(expected.toInstant(), actual.toInstant());
  }

  public static void assertThrows(Class<? extends Throwable> expectedClass, Runnable runnable) {
    assertThrows(expectedClass, "", runnable);
  }

  public static void assertThrows(Class<? extends Throwable> expectedClass, String expectedMessage, Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable actual) {
      assertEquals(expectedClass, actual.getClass());
      if (!expectedMessage.isEmpty()) {
        assertEquals(expectedMessage, actual.getMessage());
      }
      return;
    }
    fail("Expected to throw " + expectedClass.getName() + " but it does not throw");
  }

  public static void assertElapses(Duration expected, Runnable runnable, Duration delta) {
    Instant start = Instant.now();
    runnable.run();
    assertEquals(expected.toMillis(), Duration.between(start, Instant.now()).toMillis(), delta.toMillis());
  }

  public static void assertElapses(Duration expected, Runnable runnable) {
    assertElapses(expected, runnable, Duration.ofMillis(10));
  }

  public static void assertEmpty(Collection<?> collection) {
    assertEquals("Expect empty collection but found " + collection.size() + " element(s)", 0, collection.size());
  }

  public static void assertNotEmpty(Collection<?> collection) {
    assertNotEquals(0, collection.size());
  }

}
