/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.soap.internal.metadata;

import static org.mule.runtime.api.metadata.resolving.FailureCode.CONNECTION_FAILURE;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;
import org.mule.runtime.module.extension.soap.internal.runtime.connection.ForwardingSoapClient;
import org.mule.services.soap.api.client.SoapClient;
import org.mule.services.soap.api.client.metadata.SoapOperationMetadata;
import org.mule.services.soap.api.client.metadata.SoapOutputTypeBuilder;

public final class InvokeOperationOutputMetadataResolver implements OutputTypeResolver<WebServiceTypeKey> {

  private final SoapOutputTypeBuilder outputTypeBuilder = new SoapOutputTypeBuilder();

  @Override
  public String getCategoryName() {
    return "SoapConnect";
  }

  @Override
  public String getResolverName() {
    return "InvokeOutput";
  }

  @Override
  public MetadataType getOutputType(MetadataContext context, WebServiceTypeKey key)
    throws MetadataResolvingException, ConnectionException {
    ForwardingSoapClient connection = context.<ForwardingSoapClient>getConnection()
      .orElseThrow(() -> new MetadataResolvingException("Cannot obtain connection", CONNECTION_FAILURE));

    SoapClient soapClient = connection.getSoapClient(key.getService());
    SoapOperationMetadata metadata = soapClient.getMetadataResolver().getOutputMetadata(key.getOperation());
    return outputTypeBuilder.build(metadata, context.getTypeBuilder());
  }
}
