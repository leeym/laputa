package com.leeym.platform.lambda;

import com.google.inject.ImplementedBy;

@ImplementedBy(QP0P1RequestInterpreter.class)
public interface RequestInterpreter {

  InterpretedRequest interpret(String request);

}
