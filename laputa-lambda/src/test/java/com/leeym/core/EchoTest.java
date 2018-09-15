package com.leeym.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EchoTest {

  @Test
  public void test() {
    assertEquals("Foo", new Echo("Foo").process());
    assertEquals("Bar", new Echo("Bar").process());
  }
}
