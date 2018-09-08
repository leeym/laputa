package com.leeym.queries;

import org.junit.Test;

import static com.leeym.queries.Help.getTypeName;
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

  @Test
  public void testGetType() {
    assertEquals("boolean", getTypeName(boolean.class));
    assertEquals("Boolean", getTypeName(Boolean.class));
    assertEquals("int", getTypeName(int.class));
    assertEquals("Integer", getTypeName(Integer.class));
    assertEquals("float", getTypeName(float.class));
    assertEquals("Float", getTypeName(Float.class));
    assertEquals("double", getTypeName(double.class));
    assertEquals("Double", getTypeName(Double.class));
    assertEquals("String", getTypeName(String.class));
  }
}
