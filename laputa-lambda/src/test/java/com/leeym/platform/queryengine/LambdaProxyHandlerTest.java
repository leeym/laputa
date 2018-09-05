package com.leeym.platform.queryengine;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LambdaProxyHandlerTest {

  private static final LambdaProxyHandler HANDLER = new LambdaProxyHandler();

  @Test
  public void badRequest() {
    assertResponse("foobar", SC_BAD_REQUEST, "Chunk [foobar] is not a valid entry");
  }

  @Test
  public void notFound() {
    assertResponse("q=Nonexistent", SC_NOT_FOUND, "Query [Nonexistent] not found.");
  }

  @Test
  public void internalServerError() {
    assertResponse("q=Throw&p0=foobar", SC_INTERNAL_SERVER_ERROR, "foobar");
  }

  @Test
  public void hello() {
    assertResponse("q=Hello&p0=foobar", SC_OK, "Hello, foobar.");
  }

  @Test
  public void getToday() {
    assertResponse("q=GetToday", SC_OK, LocalDate.now().toString());
  }

  @Test
  public void whatDateIsToday() {
    assertResponse("q=WhatDateIsToday", SC_OK, "Today is " + LocalDate.now().toString());
  }

  @Ignore
  @Test
  public void empty() {
    assertResponse("", SC_OK, "");
  }

  @Ignore
  @Test
  public void help() {
    assertResponse("q=Help", SC_OK, "");
  }

  @Ignore
  @Test
  public void getQueries() {
    assertResponse("q=GetQueries", SC_OK, "");
  }

  private void assertResponse(String requestBody, int statusCode, String responseBody) {
    Response response = HANDLER.handleRequest(new Request(requestBody), null);
    assertEquals(response.getBody(), statusCode, response.getStatusCode());
    if (!responseBody.isEmpty()) {
      if (statusCode == SC_OK) {
        assertEquals(responseBody, response.getBody());
      } else {
        assertThat(response.getBody(), containsString(responseBody));
      }
    }
    System.out.println(response.getBody());
  }

}
