package com.leeym.queries;

public class Help extends AbstractQuery<String> {

  @Override
  public String process() {
    return "GetLocalDate()\n"
      + "GetLocalDateTime()\n"
      + "GetZonedDateTime()\n"
      + "Hello(String arg0)\n"
      + "Help()\n"
      + "Sleep(int arg0)\n"
      + "Throw(String arg0)\n";
  }

  public String realProcess() {
    return "GetLocalDate()\n"
      + "GetLocalDateTime()\n"
      + "GetZonedDateTime()\n"
      + "Hello(String arg0)\n"
      + "Help()\n"
      + "Sleep(int arg0)\n"
      + "Throw(String arg0)\n";
    /*
    return Queries.getAllQueries().stream()
      .map(aClass -> aClass.getSimpleName() + "(" + Arrays.stream(aClass.getConstructors()[0].getParameters())
        .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
        .collect(Collectors.joining(", ")) + ")")
      .sorted()
      .collect(Collectors.joining("\n"))
      + "\n";
      */
  }

}
