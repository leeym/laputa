package com.leeym.sample;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SortTest {

  @Test
  public void test() {
    assertEquals(asList("Apple", "Banana", "Cherry"), new Sort(asList("Banana", "Apple", "Cherry")).process());
  }

}
