/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.internal.connection.oauth;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.api.meta.model.connection.ConnectionProviderModel;
import org.mule.runtime.core.api.retry.RetryPolicyTemplate;
import org.mule.runtime.core.internal.connection.ReconnectableConnectionProviderWrapper;

public class OAuthConnectionProviderWrapper<C> extends ReconnectableConnectionProviderWrapper<C> {

  private final ConnectionProviderModel connectionProviderModel;

  public OAuthConnectionProviderWrapper(ConnectionProvider<C> delegate,
                                        ConnectionProviderModel connectionProviderModel,
                                        boolean disableValidation,
                                        RetryPolicyTemplate retryPolicyTemplate) {
    super(delegate, disableValidation, retryPolicyTemplate);
    this.connectionProviderModel = connectionProviderModel;
  }

  @Override
  public void initialise() throws InitialisationException {
    super.initialise();
  }
}
