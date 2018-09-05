package com.leeym.queries;

public class Help extends AbstractQuery<String> {

  @Override
  public String process() {
    return "GetToday()\n"
      + "Hello(String name)\n"
      + "Help()\n"
      + "Sleep(int seconds)\n"
      + "Throw(String message)\n"
      + "WhatDateIsToday()";
    /*
    return Queries.getAllQueries().stream()
      .map(aClass -> aClass.getSimpleName() + "(" + Arrays.stream(aClass.getConstructors()[0].getParameters())
        .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
        .collect(Collectors.joining(", ")) + ")")
      .collect(Collectors.joining("\n"));
      */
  }

}
