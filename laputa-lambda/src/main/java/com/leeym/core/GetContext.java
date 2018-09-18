package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

public class GetContext extends Query<Context> {

  @Inject
  Context context;

  @Override
  public Context process() {
    return context;
  }
}
