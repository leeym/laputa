package com.leeym.platform.queryengine;

import java.util.Collections;
import java.util.Map;

public class Response {

  private int statusCode;
  private String body;
  private Map<String, String> headers;
  private boolean isBase64Encoded;

  public Response() {
  }

  public Response(int statusCode, String body) {
    this(statusCode, body, Collections.emptyMap(), false);
  }

  public Response(int statusCode, String body, Map<String, String> headers, boolean isBase64Encoded) {
    this.statusCode = statusCode;
    this.body = body;
    this.headers = headers;
    this.isBase64Encoded = isBase64Encoded;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public boolean isBase64Encoded() {
    return isBase64Encoded;
  }

  public void setBase64Encoded(boolean base64Encoded) {
    isBase64Encoded = base64Encoded;
  }
}
