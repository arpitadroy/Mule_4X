/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.config.spring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.config.spring.InjectProviderParamsServiceProxy.createInjectProviderParamsServiceProxy;

import org.mule.runtime.api.service.Service;
import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.registry.IllegalDependencyInjectionException;
import org.mule.tck.junit4.AbstractMuleContextTestCase;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InjectProviderParamsServiceProxyTestCase extends AbstractMuleContextTestCase {

  @Rule
  public ExpectedException expected = ExpectedException.none();

  private Object augmentedParam;

  @Test
  public void notAugmentedInvocation() throws Exception {
    BaseService service = new BasicService();

    final BaseService serviceProxy = (BaseService) createInjectProviderParamsServiceProxy(service, muleContext);

    serviceProxy.augmented();

    assertThat(augmentedParam, is(true));
  }

  @Test
  public void augmentedInvocation() throws Exception {
    BaseService service = new AugmentedMethodService();

    final BaseService serviceProxy = (BaseService) createInjectProviderParamsServiceProxy(service, muleContext);

    serviceProxy.augmented();

    assertThat(augmentedParam, sameInstance(muleContext));
  }

  @Test
  public void overloadedAugmentedInvocation() throws Exception {
    BaseOverloadedService service = new OverloadedAugmentedMethodService();

    final BaseOverloadedService serviceProxy =
        (BaseOverloadedService) createInjectProviderParamsServiceProxy(service, muleContext);

    serviceProxy.augmented();

    assertThat(augmentedParam, is(true));
  }

  @Test
  public void overloadedAugmentedInvocation2() throws Exception {
    BaseOverloadedService service = new OverloadedAugmentedMethodService();

    final BaseOverloadedService serviceProxy =
        (BaseOverloadedService) createInjectProviderParamsServiceProxy(service, muleContext);

    serviceProxy.augmented(1);

    assertThat(augmentedParam, sameInstance(muleContext));
  }

  @Test
  public void ambiguousAugmentedInvocation() throws Exception {
    BaseService service = new AmbiguousAugmetedMethodService();

    final BaseService serviceProxy = (BaseService) createInjectProviderParamsServiceProxy(service, muleContext);

    expected.expect(IllegalDependencyInjectionException.class);
    expected
        .expectMessage("More than one invocation candidate for for method 'augmented' in service 'AmbiguousAugmentedMethodService'");
    serviceProxy.augmented();

    assertThat(augmentedParam, nullValue());
  }


  @Test
  public void invalidAugmentedInvocation() throws Exception {
    BaseService service = new InvalidAugmetedMethodService();

    final BaseService serviceProxy = (BaseService) createInjectProviderParamsServiceProxy(service, muleContext);

    serviceProxy.augmented();

    assertThat(augmentedParam, is(true));
  }

  public interface BaseService extends Service {

    void augmented();
  }

  public interface BaseOverloadedService extends BaseService {

    void augmented(int i);
  }

  public class BasicService implements BaseService {

    @Override
    public String getName() {
      return "BasicService";
    }

    @Override
    public void augmented() {
      augmentedParam = true;
    }

  }

  public class AugmentedMethodService implements BaseService {

    @Override
    public String getName() {
      return "AugmentedMethodService";
    }

    @Override
    public void augmented() {}

    @Inject
    void augmented(MuleContext context) {
      augmentedParam = context;
    }
  }

  public class OverloadedAugmentedMethodService implements BaseOverloadedService {

    @Override
    public String getName() {
      return "OverloadedAugmentedMethodService";
    }

    @Override
    public void augmented() {}

    @Override
    public void augmented(int i) {}

    @Inject
    void augmented(MuleContext context) {
      augmentedParam = true;
    }

    @Inject
    void augmented(int i, MuleContext context) {
      augmentedParam = context;
    }
  }

  public class AmbiguousAugmetedMethodService implements BaseService {

    @Override
    public String getName() {
      return "AmbiguousAugmentedMethodService";
    }

    @Override
    public void augmented() {}

    @Inject
    void augmented(MuleContext context) {
      augmentedParam = context;
    }

    @Inject
    void augmented(MuleContext context, MuleContext contextB) {
      augmentedParam = context;
    }
  }

  public class InvalidAugmetedMethodService implements BaseService {

    @Override
    public String getName() {
      return "InvalidAugmetedMethodService";
    }

    @Override
    public void augmented() {
      augmentedParam = true;
    }

    @Inject
    void augmented(int i) {}
  }


}
