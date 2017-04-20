/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.module.extension.oauth;

import org.mule.test.module.extension.AbstractExtensionFunctionalTestCase;
import org.mule.test.oauth.TestOAuthConnection;

import org.junit.Test;

public class OAuthExtensionTestCase extends AbstractExtensionFunctionalTestCase {

  @Override
  protected String getConfigFile() {
    return "oauth-extension-config.xml";
  }

  @Test
  public void oauthConfigurationCorrectlyParsed() throws Exception {
    TestOAuthConnection connection = (TestOAuthConnection) flowRunner("getConnection").run().getMessage().getPayload().getValue();
    connection.toString();
  }

}
