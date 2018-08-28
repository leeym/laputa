package com.leeym.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloTest {

  @Test
  public void test() {
    assertEquals("Hello, Foo.", new Hello("Foo").process());
    assertEquals("Hello, Bar.", new Hello("Bar").process());
  }
}