package com.leeym.platform.queryengine;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParsedRequestTest {

  @Test
  public void empty() {
    ParsedRequest parsedRequest = new ParsedRequest("");
    assertEquals("Help", parsedRequest.getQ());
    assertTrue(parsedRequest.getP().isEmpty());
  }

  @Test
  public void invalid1() {
    try {
      new ParsedRequest("foobar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foobar] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid2() {
    try {
      new ParsedRequest("foo&bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foo] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid3() {
    try {
      new ParsedRequest("foo=bar&baz");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [baz] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void unknownKey() {
    try {
      new ParsedRequest("q=Hello&p=bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Unknown key [p] found.", e.getMessage());
    }
  }

  @Test
  public void qNotFound() {
    try {
      new ParsedRequest("p0=foo");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Key [q] not found.", e.getMessage());
    }
  }

  @Test
  public void duplicatedQ() {
    try {
      new ParsedRequest("q=Foo&q=Bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Duplicate key [q] found.", e.getMessage());
    }
  }

  @Test
  public void valid() {
    ParsedRequest parsedRequest = new ParsedRequest("q=Hello&p0=Foo&p1=Bar");
    assertEquals("Hello", parsedRequest.getQ());
    assertEquals(Arrays.asList("Foo", "Bar"), parsedRequest.getP());
  }

  @Test
  public void longP() {
    ParsedRequest parsedRequest = new ParsedRequest("q=Hello&p0=A&p1=B&p2=C&p3=D&p4=E&p5=F&p6=G&p7=H&p8=I&p9=J&p10=K");
    assertEquals("Hello", parsedRequest.getQ());
    assertEquals(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"), parsedRequest.getP());
  }

  @Test
  public void skipP() {
    ParsedRequest parsedRequest = new ParsedRequest("q=Hello&p0=Foo&p1=Bar&p999=Baz");
    assertEquals("Hello", parsedRequest.getQ());
    assertEquals(Arrays.asList("Foo", "Bar"), parsedRequest.getP());
  }

}