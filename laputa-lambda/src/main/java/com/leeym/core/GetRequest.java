package com.leeym.core;

import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Request;

public class GetRequest extends Query<Request> {

  @Inject
  Request request;

  @Override
  public Request process() {
    return request;
  }
}
