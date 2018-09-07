package com.leeym.queries;

public class Help extends AbstractQuery<String> {

  @Override
  public String process() {
    return "Echo(String message)\n"
      + "GetLocalDate()\n"
      + "GetLocalDateTime()\n"
      + "GetZonedDateTime()\n"
      + "Help()\n"
      + "Sleep(int seconds)\n"
      + "Throw(String message)\n";
  }

  /*
  public static String generate() {
    return Queries.getAllQueries().stream()
      .map(Help::describe)
      .sorted()
      .collect(Collectors.joining("\n"));
  }

  @VisibleForTesting
  public static String describe(Class<? extends Query> queryClass) {
    return queryClass.getSimpleName() + "(" + Arrays.stream(queryClass.getConstructors()[0].getParameters())
      .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
      .collect(Collectors.joining(", ")) + ")";
  }
  */

  public String generate() {
    return process();
  }

}
