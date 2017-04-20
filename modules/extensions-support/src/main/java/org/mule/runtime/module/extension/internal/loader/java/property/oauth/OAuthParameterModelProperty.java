/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.internal.loader.java.property.oauth;

import org.mule.runtime.api.meta.model.ModelProperty;

public class OAuthParameterModelProperty implements ModelProperty {

  private final String requestAlias;

  public OAuthParameterModelProperty(String requestAlias) {
    this.requestAlias = requestAlias;
  }

  public String getRequestAlias() {
    return requestAlias;
  }

  @Override
  public String getName() {
    return "oauthParameter";
  }

  @Override
  public boolean isPublic() {
    return false;
  }
}
