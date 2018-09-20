package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Service;

public class GetContext extends Query<Context> {

  @Override
  public Context process() {
    return Service.getContext();
  }
}
