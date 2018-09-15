package com.leeym.platform.common.tuple;

public class Tuple3<A, B, C> extends Tuple2<A, B> {

  public C c;

  public Tuple3(A a, B b, C c) {
    super(a, b);
    this.c = c;
  }

  public C getC() {
    return c;
  }

  public void setC(C c) {
    this.c = c;
  }
}
