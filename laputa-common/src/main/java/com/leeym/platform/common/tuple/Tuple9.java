package com.leeym.platform.common.tuple;

public class Tuple9<A, B, C, D, E, F, G, H, I> extends Tuple8<A, B, C, D, E, F, G, H> {

  private I i;

  public Tuple9(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
    super(a, b, c, d, e, f, g, h);
    this.i = i;
  }

  public I getI() {
    return i;
  }

  public void setI(I i) {
    this.i = i;
  }
}
