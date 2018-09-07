package com.leeym.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpTest {

  @Test
  public void test() {
    assertEquals(new Help().generate(), new Help().process());
  }

  @Test
  public void testGenerate() {
    System.out.println(new Help().generate());
  }

  /*
  @Test
  public void testDescribe() {
    assertEquals("Help()", Help.describe(Help.class));
  }
  */
}