package com.leeym.core;

import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Request;
import com.leeym.platform.lambda.Service;

public class GetRequest extends Query<Request> {

  @Override
  public Request process() {
    return Service.getRequest();
  }
}
