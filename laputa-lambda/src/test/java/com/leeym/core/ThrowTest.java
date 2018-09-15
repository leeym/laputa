package com.leeym.core;

import org.junit.Test;

import static com.leeym.platform.testing.Assert.assertThrows;

public class ThrowTest {

  @Test
  public void test() {
    assertThrows(RuntimeException.class, () -> new Throw("").process());
  }

  @Test
  public void testWithMessage() {
    assertThrows(RuntimeException.class, "foobar", () -> new Throw("foobar").process());
  }
}
