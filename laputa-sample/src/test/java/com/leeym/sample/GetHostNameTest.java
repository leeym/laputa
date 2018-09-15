package com.leeym.sample;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class GetHostNameTest {

  @Test
  public void test() {
    assertNotEquals("", new GetHostName().process());
  }
}
