package com.leeym.core;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Request;

public class GetRequest extends Query<String> {

  @Inject
  Request request;

  @Override
  public String process() {
    return new Gson().toJson(request);
  }
}
