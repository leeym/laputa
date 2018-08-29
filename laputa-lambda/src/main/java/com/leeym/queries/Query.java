package com.leeym.queries;

public interface Query<T> {

  String METHOD_NAME = "process";

  T process();

}
