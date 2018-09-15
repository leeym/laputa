package com.leeym.platform.common.tuple;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class Tuple9Test {

  @Test
  public void test() {
    Tuple9<Byte, Integer, Long, Character, Float, Double, Boolean, String, BigDecimal> tuple9
      = new Tuple9<>(Byte.MAX_VALUE, 1, 2L, 'c', 3.5F, 4D, true, "str", new BigDecimal(100));
    assertEquals(Byte.MAX_VALUE, tuple9.getA().byteValue());
    assertEquals(1, tuple9.getB().intValue());
    assertEquals(2L, tuple9.getC().longValue());
    assertEquals('c', tuple9.getD().charValue());
    assertEquals(3.5F, tuple9.getE(), 0);
    assertEquals(4D, tuple9.getF(), 0);
    assertEquals(true, tuple9.getG());
    assertEquals("str", tuple9.getH());
    assertEquals(new BigDecimal(100), tuple9.getI());
  }
}
