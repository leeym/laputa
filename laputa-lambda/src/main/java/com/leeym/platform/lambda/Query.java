package com.leeym.platform.lambda;

public interface Query<T> {

  String METHOD_NAME = "process";

  T process();

}
