package org.mule.services.soap.api.client.metadata;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.MetadataType;

public interface SoapTypeBuilder {

  String BODY_FIELD = "body";
  String HEADERS_FIELD = "headers";
  String ATTACHMENTS_FIELD = "attachments";

  MetadataType build(SoapOperationMetadata metadata, BaseTypeBuilder builder);
}
