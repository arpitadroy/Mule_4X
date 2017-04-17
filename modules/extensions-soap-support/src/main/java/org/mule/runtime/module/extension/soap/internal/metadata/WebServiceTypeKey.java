/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.soap.internal.metadata;

import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyPart;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * Represents a multilevel metadata key that describes a soap operation giving a web service.
 *
 * @since 4.0
 */
public final class WebServiceTypeKey {

  @MetadataKeyPart(order = 1)
  @Parameter
  private String service;

  @MetadataKeyPart(order = 2)
  @Parameter
  private String operation;

  public WebServiceTypeKey(String service, String operation) {
    this.service = service;
    this.operation = operation;
  }

  public String getService() {
    return service;
  }

  public String getOperation() {
    return operation;
  }
}
