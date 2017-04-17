/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.module.extension.soap;

import static org.mule.test.module.extension.soap.InvokeOperationExecutionTestCase.SOAP_CONFIG;
import org.mule.test.module.extension.metadata.AbstractMetadataOperationTestCase;

public class InvokeMetadataTestCase extends AbstractMetadataOperationTestCase {

  public InvokeMetadataTestCase(ResolutionType resolutionType) {
    super(resolutionType);
  }

  @Override
  protected String getConfigFile() {
    return SOAP_CONFIG;
  }



}
