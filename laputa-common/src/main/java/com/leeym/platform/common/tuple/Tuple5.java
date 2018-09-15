package com.leeym.platform.common.tuple;

public class Tuple5<A, B, C, D, E> extends Tuple4<A, B, C, D> {

  private E e;

  public Tuple5(A a, B b, C c, D d, E e) {
    super(a, b, c, d);
    this.e = e;
  }

  public E getE() {
    return e;
  }

  public void setE(E e) {
    this.e = e;
  }
}
