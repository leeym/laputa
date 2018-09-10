package com.leeym.sample;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortTest {

  @Test
  public void test() {
    assertEquals(Arrays.asList("Apple", "Banana", "Cherry"), new Sort(Arrays.asList("Banana", "Apple", "Cherry")).process());
  }

}
