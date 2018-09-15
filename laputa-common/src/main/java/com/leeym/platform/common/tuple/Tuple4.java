package com.leeym.platform.common.tuple;

public class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {

  private D d;

  public Tuple4(A a, B b, C c, D d) {
    super(a, b, c);
    this.d = d;
  }

  public D getD() {
    return d;
  }

  public void setD(D d) {
    this.d = d;
  }
}
