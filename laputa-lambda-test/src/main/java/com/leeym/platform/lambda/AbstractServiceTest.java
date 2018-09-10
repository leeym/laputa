package com.leeym.platform.lambda;

import com.google.inject.TypeLiteral;
import com.kaching.platform.converters.InstantiatorModule;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static com.kaching.platform.converters.Instantiators.createConverter;
import static com.kaching.platform.converters.Instantiators.createInstantiator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class AbstractServiceTest {

  public abstract AbstractLambdaProxyHandler getHandler();

  @Test
  public void queriesArePublic() {
    getHandler().getAllQueries()
      .forEach(aClass -> assertTrue(aClass.getName(), Modifier.isPublic(aClass.getModifiers())));
  }

  @Test
  public void queriesHaveOnlyOneConstructor() {
    getHandler().getAllQueries()
      .forEach(aClass -> assertEquals(aClass.getName(), 1, aClass.getConstructors().length));
  }

  @Test
  public void queryConstructorsArePublic() {
    getHandler().getAllQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getConstructors()))
      .forEach(constructor -> assertTrue(Modifier.isPublic(constructor.getModifiers())));
  }

  @Test
  public void queryCanBeInstantiated() {
    InstantiatorModule module = getHandler().getInstantiatorModule();
    getHandler().getAllQueries().forEach(aClass -> {
      try {
        createInstantiator(aClass, module);
      } catch (Exception e) {
        throw new RuntimeException("Query [" + aClass.getSimpleName() + "] can not be instantiated", e);
      }
    });
  }

  @Test
  public void parametersCanBeConverted() {
    InstantiatorModule module = getHandler().getInstantiatorModule();
    getHandler().getAllQueries().stream()
      .flatMap(aClass -> Arrays.stream(aClass.getConstructors()))
      .flatMap(constructor -> Arrays.stream(constructor.getGenericParameterTypes()))
      .forEach(type -> {
        try {
          createConverter(TypeLiteral.get(type), module);
        } catch (Exception e) {
          throw new RuntimeException("Parameter [" + type + "] can not be converted", e);
        }
      });
  }

  @Test
  public void returnTypesCanBeConverted() {
    InstantiatorModule module = getHandler().getInstantiatorModule();
    getHandler().getAllQueries().stream()
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
          throw new RuntimeException("Return type [" + type + "] can not be converted", e);
        }
      });
  }

  @Test
  public void queriesAreTested() {
    getHandler().getAllQueries().stream()
      .filter(aClass -> !aClass.getName().startsWith(Queries.COMMON_QUERY_PACKAGE))
      .forEach(aClass -> {
        String name = aClass.getName();
        try {
          System.out.println("Looking for tests of " + name);
          Class.forName(name + "Test");
        } catch (ClassNotFoundException e) {
          fail("Query [" + aClass.getSimpleName() + "] is not tested");
        }
      });
  }


}
