/* 
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 * 
 * Copyright (c) Cubis Limited. All rights reserved.
 * http://www.cubis.co.uk 
 * 
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file. 
 */
package org.mule.transformers.codec;

import org.mule.transformers.DefaultTransformer;
import org.mule.umo.transformer.TransformerException;

/**
 * <code>Base64Encoder</code> transforms strings or byte arrays into UU encoded
 * string
 *
 * @author <a href="mailto:ross.mason@cubis.co.uk">Ross Mason</a>
 * @version $Revision$
 */
public class UCEncoder extends DefaultTransformer
{
    private sun.misc.UCEncoder encoder;
    
    public UCEncoder()
    {
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        setReturnClass(String.class);
        encoder = new sun.misc.UCEncoder();
    }

    /* (non-Javadoc)
     * @see org.mule.umo.transformer.UMOTransformer#transform(java.lang.Object)
     */
    public Object doTransform(Object src) throws TransformerException
    {
        byte[] buf;
        if(src instanceof String) {
            buf = src.toString().getBytes();
        } else {
            buf = (byte[])src;
        }
        String result = encoder.encode(buf);
        if(getReturnClass().equals(byte[].class)) {
            return result.getBytes();
        }
        return result;
    }
}
