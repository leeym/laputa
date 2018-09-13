package com.leeym.core;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.AbstractService;
import com.leeym.platform.lambda.Query;

import java.util.Set;

public class CoreService extends AbstractService {

  @Override
  public Set<Class<? extends Query>> getQueries() {
    return ImmutableSet.of(
      Echo.class,
      Help.class,
      Throw.class
    );
  }

  @Override
  public Package getPackage() {
    return CoreService.class.getPackage();
  }

  @Override
  public InstantiatorModule getInstantiatorModule() {
    return binder -> {
    };
  }

  @Override
  public Module getModule() {
    return binder -> {
    };
  }
}
