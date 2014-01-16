/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.ws.functional;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WSConsumerFunctionalTestCase extends AbstractWSConsumerFunctionalTestCase
{

    private final String configFile;

    public WSConsumerFunctionalTestCase(String configFile)
    {
        this.configFile = configFile;
    }

    @Override
    protected String getConfigFile()
    {
        return configFile;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters()
    {
        return Arrays.asList(new Object[] {"ws-consumer-http-config.xml"},
                             new Object[] {"ws-consumer-http-custom-endpoint-config.xml"},
                             new Object[] {"ws-consumer-https-config.xml"},
                             new Object[] {"ws-consumer-jms-config.xml"});
    }

    @Test
    public void validRequestReturnsExpectedAnswer() throws Exception
    {
        assertValidResponse("vm://in");
    }

    @Test
    public void invalidRequestFormatReturnsSOAPFault() throws Exception
    {
        String message = "<tns:echo xmlns:tns=\"http://consumer.ws.module.mule.org/\"><invalid>Hello</invalid></tns:echo>";
        assertSoapFault("vm://in", message, "Client");
    }

    @Test
    public void invalidNamespaceReturnsSOAPFault() throws Exception
    {
        String message = "<tns:echo xmlns:tns=\"http://invalid/\"><text>Hello</text></tns:echo>";
        assertSoapFault("vm://in", message, "Client");
    }

}
