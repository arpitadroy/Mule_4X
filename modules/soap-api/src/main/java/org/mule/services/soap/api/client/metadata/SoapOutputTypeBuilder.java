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
import org.mule.metadata.api.model.NullType;
import org.mule.metadata.api.visitor.MetadataTypeVisitor;
import org.mule.runtime.api.util.Reference;

public class SoapOutputTypeBuilder implements SoapTypeBuilder {

  @Override
  public MetadataType build(SoapOperationMetadata metadata, BaseTypeBuilder builder) {
    Reference<MetadataType> result = new Reference<>();
    metadata.getAttachmentsType().accept(new MetadataTypeVisitor() {

      @Override
      protected void defaultVisit(MetadataType metadataType) {
        ObjectTypeBuilder object = builder.objectType();
        object.addField().key(BODY_FIELD).value(metadata.getBodyType());
        object.addField().key(ATTACHMENTS_FIELD).value().arrayType().of(builder.anyType());
        result.set(object.build());
      }

      @Override
      public void visitNull(NullType nullType) {
        result.set(metadata.getBodyType());
      }
    });

    return result.get();
  }
}
