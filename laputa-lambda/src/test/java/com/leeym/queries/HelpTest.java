package com.leeym.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpTest {

  @Test
  public void test() {
    System.out.println(new Help().process());
  }

  @Test
  public void testDescribe() {
    assertEquals("Help()\n", Help.describe(Help.class));
  }
}
