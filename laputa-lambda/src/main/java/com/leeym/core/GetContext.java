package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;

public class GetContext extends Query<String> {

  @Inject
  Context context;

  @Override
  public String process() {
    return new Gson().toJson(context);
  }
}
