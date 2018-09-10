package com.leeym.platform.lambda;

import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public abstract class AbstractLambdaProxyHandlerTest {

  public abstract AbstractLambdaProxyHandler getHandler();

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
  public void echo() {
    assertResponse("q=Echo&p0=foobar", SC_OK, "foobar");
  }

  @Test
  public void empty() {
    assertResponse("", SC_OK, "");
  }

  @Test
  public void help() {
    assertResponse("q=Help", SC_OK, "");
  }

  private void assertResponse(String requestBody, int statusCode, String responseBody) {
    Response response = getHandler().handleRequest(new Request(requestBody), null);
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
