package com.leeym.platform.lambda;

import java.util.List;

public class InterpretedRequest {

  private final String query;
  private final List<String> parameters;

  public InterpretedRequest(String query, List<String> parameters) {
    this.query = query;
    this.parameters = parameters;
  }

  public String getQuery() {
    return query;
  }

  public List<String> getParameters() {
    return parameters;
  }
}
