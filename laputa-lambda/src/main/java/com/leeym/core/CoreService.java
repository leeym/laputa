package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.kaching.platform.converters.AbstractInstantiatorModule;
import com.leeym.platform.common.sleeper.DefaultSleeper;
import com.leeym.platform.common.sleeper.Sleeper;
import com.leeym.platform.converters.GsonConverter;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Service;

import java.util.Set;

public class CoreService extends Service {

  public static Set<Class<? extends Query>> queries = ImmutableSet.of(
    Echo.class,
    GetContext.class,
    GetRequest.class,
    Help.class,
    Sleep.class,
    Throw.class
  );

  @Override
  public Set<Class<? extends Query>> getQueries() {
    return queries;
  }

  @Override
  public Package getPackage() {
    return CoreService.class.getPackage();
  }

  @Override
  public AbstractInstantiatorModule getInstantiatorModule() {
    return new AbstractInstantiatorModule() {
      @Override
      protected void configure() {
        registerFor(APIGatewayV2ProxyRequestEvent.class).converter(new GsonConverter<>());
        registerFor(Context.class).converter(new GsonConverter<>());
      }
    };
  }

  @Override
  public AbstractModule getModule() {
    return new AbstractModule() {
      @Override
      protected void configure() {
        bind(Sleeper.class).toInstance(new DefaultSleeper());
      }
    };
  }
}
