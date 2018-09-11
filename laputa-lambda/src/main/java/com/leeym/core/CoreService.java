package com.leeym.core;

import com.google.inject.Module;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.AbstractService;
import com.leeym.platform.lambda.Query;

import java.util.Set;

public class CoreService extends AbstractService {

  @Override
  public Set<Class<? extends Query>> getAllQueries() {
    return new Queries().getAllQueries("");
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
