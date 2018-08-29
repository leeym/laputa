package com.leeym.platform.queryengine;

import org.junit.Test;

import java.time.LocalDate;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class LambdaProxyHandlerTest {

  private static final LambdaProxyHandler HANDLER = new LambdaProxyHandler();

  @Test
  public void badRequest() {
    Response response = HANDLER.handleRequest(new Request("foobar"), null);
    assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    assertEquals("Chunk [foobar] is not a valid entry", response.getBody());
  }

  @Test
  public void notFound() {
    Response response = HANDLER.handleRequest(new Request("q=Nonexistent"), null);
    assertEquals(SC_NOT_FOUND, response.getStatusCode());
    assertEquals("Query [Nonexistent] not found.", response.getBody());
  }

  @Test
  public void internalServerError() {
    Response response = HANDLER.handleRequest(new Request("q=Throw&p0=foobar"), null);
    assertEquals(SC_INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("foobar", response.getBody());
  }

  @Test
  public void hello() {
    Response response = HANDLER.handleRequest(new Request("q=Hello&p0=foobar"), null);
    assertEquals(SC_OK, response.getStatusCode());
    assertEquals("Hello, foobar.", response.getBody());
  }

  @Test
  public void getToday() {
    Response response = HANDLER.handleRequest(new Request("q=GetToday"), null);
    assertEquals(SC_OK, response.getStatusCode());
    assertEquals(LocalDate.now().toString(), response.getBody());
  }

  @Test
  public void whatDateIsToday() {
    Response response = HANDLER.handleRequest(new Request("q=WhatDateIsToday"), null);
    assertEquals(SC_OK, response.getStatusCode());
    assertEquals("Today is " + LocalDate.now().toString(), response.getBody());
  }

}