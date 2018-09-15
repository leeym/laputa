package com.leeym.platform.common.tuple;

public class Tuple6<A, B, C, D, E, F> extends Tuple5<A, B, C, D, E> {

  private F f;

  public Tuple6(A a, B b, C c, D d, E e, F f) {
    super(a, b, c, d, e);
    this.f = f;
  }

  public F getF() {
    return f;
  }

  public void setF(F f) {
    this.f = f;
  }
}
