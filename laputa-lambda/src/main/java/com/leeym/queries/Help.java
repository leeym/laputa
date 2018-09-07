package com.leeym.queries;

public class Help extends AbstractQuery<String> {

  @Override
  public String process() {
    return "GetLocalDate()\n"
      + "GetLocalDateTime()\n"
      + "GetZonedDateTime()\n"
      + "Echo(String name)\n"
      + "Help()\n"
      + "Sleep(int seconds)\n"
      + "Throw(String message)\n";
  }

}
