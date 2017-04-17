/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.soap.internal.metadata;

import static org.mule.runtime.api.metadata.resolving.FailureCode.CONNECTION_FAILURE;
import static org.mule.runtime.module.extension.internal.metadata.MultilevelMetadataKeyBuilder.newKey;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;
import org.mule.runtime.extension.api.soap.WebServiceDefinition;
import org.mule.runtime.module.extension.soap.internal.runtime.connection.ForwardingSoapClient;
import org.mule.services.soap.api.client.metadata.SoapMetadataResolver;

import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

public final class InvokeOperationKeysResolver implements TypeKeysResolver {

  @Override
  public String getCategoryName() {
    return "SoapConnect";
  }

  @Override
  public Set<MetadataKey> getKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException {
    ForwardingSoapClient connection = context.<ForwardingSoapClient>getConnection()
      .orElseThrow(() -> new MetadataResolvingException("Cannot obtain connection", CONNECTION_FAILURE));
    ImmutableSet.Builder<MetadataKey> keys = ImmutableSet.builder();
    connection.getAllWebServices().forEach(ws -> keys.add(buildServiceKey(connection, ws)));
    return keys.build();
  }

  private MetadataKey buildServiceKey(ForwardingSoapClient connection, WebServiceDefinition ws) {
    String serviceId = ws.getServiceId();
    SoapMetadataResolver resolver = connection.getSoapClient(serviceId).getMetadataResolver();
    MetadataKeyBuilder key = newKey(serviceId).withDisplayName(ws.getFriendlyName());
    List<String> excludedOperations = ws.getExcludedOperations();
    resolver.getAvailableOperations().stream()
      .filter(ope -> !excludedOperations.contains(ope))
      .forEach(ope -> key.withChild(newKey(ope)));
    return key.build();
  }
}
