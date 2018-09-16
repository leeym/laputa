package com.leeym.platform.lambda;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.leeym.platform.common.chronograph.Chronograph;

import java.lang.reflect.Method;

public class ProfilingModule extends AbstractModule {

  private final Chronograph chronograph;
  private Package[] packages;

  public ProfilingModule(Chronograph chronograph, Package... packages) {
    this.chronograph = chronograph;
    this.packages = packages;
  }

  @Override
  protected void configure() {
    bindInterceptor(Matchers.any(), Matchers.any(), methodInvocation -> {
      Method method = methodInvocation.getMethod();
      return chronograph.time(method.getDeclaringClass(), method.getName(), () -> {
        try {
          return methodInvocation.proceed();
        } catch (Throwable e) {
          throw new RuntimeException(e);
        }
      });
    });
  }

}
