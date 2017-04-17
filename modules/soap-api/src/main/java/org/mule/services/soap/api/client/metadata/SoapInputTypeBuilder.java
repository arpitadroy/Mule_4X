/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.services.soap.api.client.metadata;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;

public class SoapInputTypeBuilder implements SoapTypeBuilder {

  @Override
  public MetadataType build(SoapOperationMetadata metadata, BaseTypeBuilder builder) {
    ObjectTypeBuilder object = builder.objectType();
    object.addField().key(HEADERS_FIELD).value(metadata.getHeadersType());
    object.addField().key(BODY_FIELD).value(metadata.getBodyType());
    object.addField().key(ATTACHMENTS_FIELD).value(metadata.getAttachmentsType());
    return object.build();
  }
}
