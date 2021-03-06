/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.compatibility.config.spring.parsers.specific.endpoint;

import org.mule.compatibility.config.spring.parsers.specific.endpoint.support.OrphanEndpointDefinitionParser;
import org.mule.compatibility.core.endpoint.EndpointURIEndpointBuilder;

public class GenericGlobalEndpointDefinitionParser extends OrphanEndpointDefinitionParser {

  public GenericGlobalEndpointDefinitionParser() {
    super(EndpointURIEndpointBuilder.class);
  }

}
