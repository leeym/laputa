package com.leeym.platform.lambda;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QP0P1RequestInterpreterTest {

  private static final RequestInterpreter INTERPRETER = new QP0P1RequestInterpreter();

  @Test
  public void empty() {
    InterpretedRequest interpretedRequest = INTERPRETER.interpret("");
    assertEquals("Help", interpretedRequest.getQuery());
    assertTrue(interpretedRequest.getParameters().isEmpty());
  }

  @Test
  public void invalid1() {
    try {
      INTERPRETER.interpret("foobar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foobar] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid2() {
    try {
      INTERPRETER.interpret("foo&bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [foo] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void invalid3() {
    try {
      INTERPRETER.interpret("foo=bar&baz");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Chunk [baz] is not a valid entry", e.getMessage());
    }
  }

  @Test
  public void unknownKey() {
    try {
      INTERPRETER.interpret("q=Echo&p=bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Unknown key [p] found.", e.getMessage());
    }
  }

  @Test
  public void qNotFound() {
    try {
      INTERPRETER.interpret("p0=foo");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Key [q] not found.", e.getMessage());
    }
  }

  @Test
  public void duplicatedQ() {
    try {
      INTERPRETER.interpret("q=Foo&q=Bar");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Duplicate key [q] found.", e.getMessage());
    }
  }

  @Test
  public void valid() {
    InterpretedRequest req = INTERPRETER.interpret("q=Echo&p0=Foo&p1=Bar");
    assertEquals("Echo", req.getQuery());
    assertEquals(Arrays.asList("Foo", "Bar"), req.getParameters());
  }

  @Test
  public void longP() {
    InterpretedRequest req = INTERPRETER.interpret("q=Echo&p0=A&p1=B&p2=C&p3=D&p4=E&p5=F&p6=G&p7=H&p8=I&p9=J&p10=K");
    assertEquals("Echo", req.getQuery());
    assertEquals(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"), req.getParameters());
  }

  @Test
  public void skipP() {
    InterpretedRequest req = INTERPRETER.interpret("q=Echo&p0=Foo&p1=Bar&p999=Baz");
    assertEquals("Echo", req.getQuery());
    assertEquals(Arrays.asList("Foo", "Bar"), req.getParameters());
  }

}
