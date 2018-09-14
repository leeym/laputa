package com.leeym.core;

import com.leeym.platform.lambda.Query;

public class GetHeader extends Query<String> {

  private final String headerName;

  public GetHeader(String headerName) {
    this.headerName = headerName;
  }

  @Override
  public String process() {
    return getRequest().getHeaders().get(headerName);
  }
}
