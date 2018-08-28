package com.leeym;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RequestTest {

  @Test
  public void empty() {
    Request request = new Request("");
    assertEquals("Help", request.getQ());
    assertTrue(request.getP().isEmpty());
  }

  @Test
  public void invalid1() {
    try {
      new Request("foobar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foobar] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid2() {
    try {
      new Request("foo&bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foo] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid3() {
    try {
      new Request("foo=bar&baz");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [baz] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void unknownKey() {
    try {
      new Request("q=Hello&p=bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Unknown key [p] found.", e.getMessage());
    }
  }

  @Test
  public void qNotFound() {
    try {
      new Request("p0=foo");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Key [q] not found.", e.getMessage());
    }
  }

  @Test
  public void duplicatedQ() {
    try {
      new Request("q=Foo&q=Bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Duplicate key [q] found.", e.getMessage());
    }
  }

  @Test
  public void valid() {
    Request request = new Request("q=Hello&p0=Foo&p1=Bar");
    assertEquals("Hello", request.getQ());
    assertEquals(Arrays.asList("Foo", "Bar"), request.getP());
  }

  @Test
  public void longP() {
    Request request = new Request("q=Hello&p0=A&p1=B&p2=C&p3=D&p4=E&p5=F&p6=G&p7=H&p8=I&p9=J&p10=K");
    assertEquals("Hello", request.getQ());
    assertEquals(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"), request.getP());
  }

  @Test
  public void skipP() {
    Request request = new Request("q=Hello&p0=Foo&p1=Bar&p999=Baz");
    assertEquals("Hello", request.getQ());
    assertEquals(Arrays.asList("Foo", "Bar"), request.getP());
  }

}