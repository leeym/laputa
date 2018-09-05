package com.leeym.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpTest {

  @Test
  public void test() {
    assertEquals(new Help().process(), new Help().realProcess());
  }

  @Test
  public void realTest() {
    System.out.println(new Help().realProcess());
  }
}