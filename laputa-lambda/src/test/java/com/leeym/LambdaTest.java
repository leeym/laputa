package com.leeym;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class LambdaTest {

  private static final Lambda lambda = new Lambda();

  @Test
  public void echo() {
    assertEquals("Hello, Foo.", lambda.handleRequest("q=Hello&p0=Foo", null));
    assertEquals("Hello, Bar.", lambda.handleRequest("q=Hello&p0=Bar", null));
  }

  @Test
  public void getToday() {
    assertEquals(LocalDate.now().toString(), lambda.handleRequest("q=GetToday", null));
  }

  @Test
  public void whatDateIsToday() {
    assertEquals("Today is " + LocalDate.now().toString(), lambda.handleRequest("q=WhatDateIsToday", null));
  }

}