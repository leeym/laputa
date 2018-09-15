package com.leeym.sample;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class GetHostAddressTest {

  @Test
  public void test() {
    assertNotEquals("", new GetHostAddress().process());
  }
}
