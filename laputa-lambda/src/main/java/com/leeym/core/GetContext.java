package com.leeym.core;

import com.google.gson.Gson;
import com.leeym.platform.lambda.Query;

public class GetContext extends Query<String> {

  @Override
  public String process() {
    return new Gson().toJson(getContext());
  }
}