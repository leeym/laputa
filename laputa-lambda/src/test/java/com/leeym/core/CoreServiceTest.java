package com.leeym.core;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.google.common.collect.ImmutableSet;
import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Service;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kaching.platform.converters.Instantiators.createConverter;
import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CoreServiceTest {

  private final Service service = new CoreService();
  private final Context context = new SimpleContext();

  @Test
  public void badRequest() {
    assertResponse("foobar", SC_BAD_REQUEST, "Chunk [foobar] is not a valid entry");
  }

  @Test
  public void notFound() {
    assertResponse("q=Nonexistent", SC_NOT_FOUND, "Query [Nonexistent] not found.");
  }

  @Test
  public void internalServerError() {
    assertResponse("q=Throw&p0=foobar", SC_INTERNAL_SERVER_ERROR, "foobar");
  }

  @Test
  public void echo() {
    assertResponse("q=Echo&p0=foobar", SC_OK, "foobar");
  }

  @Test
  public void empty() {
    assertResponse("", SC_OK, "");
  }

  @Test
  public void help() {
    assertResponse("q=Help", SC_OK, "");
  }

  @Test
  public void getRequest() {
    assertResponse("q=GetRequest", SC_OK, "");
  }

  @Test
  public void getContext() {
    assertResponse("q=GetContext", SC_OK, "");
  }

  private void assertResponse(String requestBody, int statusCode, String responseBody) {
    APIGatewayV2ProxyRequestEvent request = new APIGatewayV2ProxyRequestEvent();
    request.setBody(requestBody);
    APIGatewayV2ProxyResponseEvent response = service.handleRequest(request, context);
    assertEquals(response.getBody(), statusCode, response.getStatusCode());
    if (!responseBody.isEmpty()) {
      if (statusCode == SC_OK) {
        assertEquals(responseBody, response.getBody());
      } else {
        assertThat(response.getBody(), containsString(responseBody));
      }
    }
  }

  // AbstractServiceTest
  private Service getService() {
    return service;
  }

  private Set<Class<? extends Query>> getQueries() {
    return new Reflections(getService().getPackage().getName()).getSubTypesOf(Query.class).stream()
      .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
      .collect(Collectors.toSet());
  }

  @Test
  public void queriesAreExposed() {
    assertEquals(getQueries(), getService().getQueries());
  }

  @Test
  public void queriesArePublic() {
    getService().getQueries()
      .forEach(aClass -> assertTrue(aClass.getName(), Modifier.isPublic(aClass.getModifiers())));
  }

  @Test
  public void queriesHaveOnlyOneConstructor() {
    getService().getQueries()
      .forEach(aClass -> assertEquals(aClass.getName(), 1, aClass.getConstructors().length));
  }

  @Test
  public void queryConstructorsArePublic() {
    getService().getQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getConstructors()))
      .forEach(constructor -> assertTrue(Modifier.isPublic(constructor.getModifiers())));
  }

  @Test
  public void queryCanBeInstantiated() {
    InstantiatorModule module = getService().getInstantiatorModule();
    getService().getQueries().forEach(aClass -> {
      try {
        createInstantiator(aClass, module);
      } catch (Exception e) {
        fail("Query [" + aClass.getSimpleName() + "] can not be instantiated: " + e);
      }
    });
  }

  @Test
  public void parametersCanBeConverted() {
    InstantiatorModule module = getService().getInstantiatorModule();
    getService().getQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getConstructors()))
      .flatMap(constructor -> Arrays.stream(constructor.getGenericParameterTypes()))
      .forEach(type -> {
        try {
          createConverter(TypeLiteral.get(type), module);
        } catch (Exception e) {
          fail("Parameter [" + type + "] can not be converted: " + e);
        }
      });
  }

  @Test
  public void returnTypesCanBeConverted() {
    InstantiatorModule module = getService().getInstantiatorModule();
    getService().getQueries().stream()
      .map(aClass -> {
        try {
          return aClass.getDeclaredMethod(Query.METHOD_NAME).getGenericReturnType();
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
      })
      .forEach(type -> {
        try {
          createConverter(TypeLiteral.get(type), module);
        } catch (Exception e) {
          fail("Return type [" + type + "] can not be converted, please update InstantiatorModule: " + e);
        }
      });
  }

  @Test
  public void queriesAreTested() {
    getService().getQueries().stream()
      .filter(aClass -> !aClass.getName().startsWith(CoreService.class.getPackage().getName()))
      .forEach(aClass -> {
        String name = aClass.getName();
        try {
          Class.forName(name + "Test");
        } catch (ClassNotFoundException e) {
          fail("Query [" + aClass.getSimpleName() + "] is not tested: " + e);
        }
      });
  }

  @Test
  public void injectedFieldsAreBound() {
    Injector injector = getService().createInjector();
    getService().getQueries().stream()
      .filter(aClass -> !ImmutableSet.of(GetRequest.class, GetContext.class).contains(aClass))
      .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
      .filter(field -> field.isAnnotationPresent(Inject.class))
      .forEach(field -> {
        try {
          injector.getInstance(Key.get(field.getGenericType()));
        } catch (ConfigurationException e) {
          fail("Field [" + field.getGenericType() + "] is not bound, please update Module: " + e);
        }
      });
  }

}
