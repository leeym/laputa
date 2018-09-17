package com.leeym.platform.lambda;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;
import com.leeym.platform.common.chronograph.Chronograph;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

public class ProfilingModule extends AbstractModule {

  private Package[] packages;

  public ProfilingModule(Package... packages) {
    this.packages = packages;
  }

  @Override
  protected void configure() {
    MethodProfiler methodProfiler = new MethodProfiler();
    requestInjection(methodProfiler);
    bindInterceptor(new AbstractMatcher<Class<?>>() {
      @Override
      public boolean matches(Class<?> aClass) {
        return Arrays.stream(packages).anyMatch(aPackage -> aClass.getName().startsWith(aPackage.getName()));
      }
    }, Matchers.any(), methodProfiler);
  }

  public class MethodProfiler implements MethodInterceptor {

    @Inject
    Chronograph chronograph;

    @Override
    public Object invoke(MethodInvocation methodInvocation) {
      return chronograph.time(
        methodInvocation.getMethod().getDeclaringClass(),
        methodInvocation.getMethod().getName(),
        () -> {
          try {
            return methodInvocation.proceed();
          } catch (Throwable e) {
            throw new RuntimeException(e);
          }
        });
    }
  }
}
