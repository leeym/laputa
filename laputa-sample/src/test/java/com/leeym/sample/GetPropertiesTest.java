package com.leeym.sample;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class GetPropertiesTest {

  @Test
  public void test() {
    assertNotEquals("", new GetProperties().process());
  }

}
