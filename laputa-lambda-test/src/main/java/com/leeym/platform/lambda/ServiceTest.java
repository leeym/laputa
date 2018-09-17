package com.leeym.platform.lambda;

import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.kaching.platform.converters.InstantiatorModule;
import com.leeym.core.CoreService;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kaching.platform.converters.Instantiators.createConverter;
import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class ServiceTest {

  public abstract Service getService();

  private Set<Class<?>> getQueries() {
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
    Injector injector = Guice.createInjector(getService().getModule());
    getService().getQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
      .filter(field -> field.isAnnotationPresent(Inject.class))
      .forEach(field -> {
        try {
          injector.getInstance(field.getType());
        } catch (ConfigurationException e) {
          fail("Field [" + field.getType().getSimpleName() + "] is not bound, please update Module: " + e);
        }
      });
  }

  @Test
  public void testGetRevision() {
    Optional<String> maybeRevision = getService().getRevision();
    assertTrue(maybeRevision.isPresent());
    assertNotEquals("", maybeRevision.get());
    System.out.println(maybeRevision.get());
  }

}
