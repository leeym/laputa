package com.leeym.sample;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UniqTest {

  @Test
  public void test() {
    assertEquals(ImmutableSet.of("A", "B", "C"), new Uniq(Arrays.asList("A", "C", "B", "B", "A")).process());
  }

}
