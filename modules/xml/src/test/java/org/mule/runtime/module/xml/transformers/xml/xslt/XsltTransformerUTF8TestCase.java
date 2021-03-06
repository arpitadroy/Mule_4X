/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.xml.transformers.xml.xslt;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.core.api.transformer.Transformer;
import org.mule.runtime.core.util.IOUtils;
import org.mule.runtime.module.xml.transformer.XsltTransformer;
import org.mule.runtime.module.xml.transformers.xml.AbstractXmlTransformerTestCase;

public class XsltTransformerUTF8TestCase extends AbstractXmlTransformerTestCase {

  private String srcData;
  private String resultData;

  @Override
  protected void doSetUp() throws Exception {
    srcData = IOUtils.toString(IOUtils.getResourceAsStream("cdcatalog-utf-8.xml", getClass()), "UTF-8");
    resultData = IOUtils.toString(IOUtils.getResourceAsStream("cdcatalog-utf-8.html", getClass()), "UTF-8");
  }

  @Override
  public Transformer getTransformer() throws Exception {
    XsltTransformer transformer = new XsltTransformer();
    transformer.setXslFile("cdcatalog.xsl");
    transformer.setReturnDataType(DataType.STRING);
    transformer.setMuleContext(muleContext);
    transformer.initialise();
    return transformer;
  }

  @Override
  public Transformer getRoundTripTransformer() throws Exception {
    return null;
  }

  @Override
  public void testRoundtripTransform() throws Exception {
    // disable this test
  }

  @Override
  public Object getTestData() {
    return srcData;
  }

  @Override
  public Object getResultData() {
    return resultData;
  }

}
