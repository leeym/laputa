package com.leeym.platform.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tuple3Test {

  @Test
  public void test() {
    Tuple3<String, Integer, Boolean> tuple3 = new Tuple3<>("foo", 12, true);
    assertEquals("foo", tuple3.getA());
    assertEquals(12, tuple3.getB().intValue());
    assertEquals(true, tuple3.getC());
    tuple3.setA("bar");
    tuple3.setB(17);
    tuple3.setC(false);
    assertEquals("bar", tuple3.getA());
    assertEquals(17, tuple3.getB().intValue());
    assertEquals(false, tuple3.getC());
  }
}