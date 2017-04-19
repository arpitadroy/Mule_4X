/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.config.spring;

import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Arrays.asList;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.core.util.ClassUtils.findImplementedInterfaces;

import org.mule.runtime.api.service.Service;
import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.registry.IllegalDependencyInjectionException;
import org.mule.runtime.core.api.registry.RegistrationException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Proxies a {@link Service} instance to augment invocations of implementation methods with {@link Inject} annotations.
 */
public class InjectProviderParamsServiceProxy implements InvocationHandler {

  private final Service service;
  private final MuleContext context;

  /**
   * Creates a new proxy for the provided service instance.
   *
   * @param service service instance to wrap. Non null.
   * @param context the {@link MuleContext} to use for resolving injectable parameters. Non null.
   */
  public InjectProviderParamsServiceProxy(Service service, MuleContext context) {
    checkArgument(service != null, "service cannot be null");
    checkArgument(context != null, "context cannot be null");
    this.service = service;
    this.context = context;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      Method candidate = null;

      for (Method serviceImplMethod : service.getClass().getDeclaredMethods()) {
        if (serviceImplMethod.getName().equals(method.getName())
            && serviceImplMethod.getAnnotationsByType(Inject.class).length > 0
            && equivalentParams(method.getParameterTypes(), serviceImplMethod.getParameterTypes())) {
          if (candidate != null) {
            throw new IllegalDependencyInjectionException(String
                .format("More than one invocation candidate for for method '%s' in service '%s'", method.getName(),
                        service.getName()));
          }
          candidate = serviceImplMethod;
        }
      }

      if (candidate == null) {
        return method.invoke(service, args);
      } else {
        final List<Object> augmentedArgs = args == null ? new ArrayList<>() : new ArrayList<>(asList(args));

        for (int j = method.getParameterTypes().length; j < candidate.getParameterTypes().length; ++j) {
          augmentedArgs.add(context.getRegistry().lookupObject(candidate.getParameterTypes()[j]));
        }

        return candidate.invoke(service, augmentedArgs.toArray());
      }
    } catch (InvocationTargetException ite) {
      // Unwrap target exception to ensure InvocationTargetException (in case of unchecked exceptions) or
      // UndeclaredThrowableException (in case of checked exceptions) is not thrown by Service instead of target exception.
      throw ite.getTargetException();
    }
  }

  private boolean equivalentParams(Class<?>[] invocationParamTypes, Class<?>[] serviceImplParamTypes)
      throws RegistrationException {
    int i = 0;
    for (Class<?> invocationParamType : invocationParamTypes) {
      if (!serviceImplParamTypes[i].equals(invocationParamType)) {
        return false;
      }
      ++i;
    }

    // Check that the remaining parameters are injectable
    for (int j = i; j < serviceImplParamTypes.length; ++j) {
      if (context.getRegistry().lookupObject(serviceImplParamTypes[j]) == null) {
        return false;
      }
    }

    return true;
  }

  /**
   * Creates a proxy for the provided service instance.
   *
   * @param service service to wrap. Non null.
   * @param context the {@link MuleContext} to use for resolving injectable parameters. Non null.
   * @return a new proxy instance.
   */
  public static Service createInjectProviderParamsServiceProxy(Service service, MuleContext context) {
    checkArgument(service != null, "service cannot be null");
    checkArgument(context != null, "context cannot be null");
    InvocationHandler handler = new InjectProviderParamsServiceProxy(service, context);

    return (Service) newProxyInstance(service.getClass().getClassLoader(), findImplementedInterfaces(service.getClass()),
                                      handler);
  }
}
