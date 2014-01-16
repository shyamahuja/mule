package org.mule.module.ws.consumer;

import static org.junit.Assert.assertEquals;
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.tck.junit4.AbstractMuleContextTestCase;
import org.mule.tck.size.SmallTest;

import org.junit.Test;

@SmallTest
public class WSConsumerConfigTestCase extends AbstractMuleContextTestCase
{

    private static final String SERVICE_ADDRESS = "http://localhost";

    @Test
    public void initializeWithValidProperties() throws MuleException
    {
        WSConsumerConfig config = createConsumerConfig();
        config.initialise();
    }

    @Test(expected = InitialisationException.class)
    public void failToInitializeWithNoServiceAddressOrEndpoint() throws MuleException
    {
        WSConsumerConfig config = createConsumerConfig();
        config.setServiceAddress(null);
        config.initialise();
    }

    @Test(expected = InitialisationException.class)
    public void failToInitializeWithBothServiceAddressAndEndpoint() throws MuleException
    {
        WSConsumerConfig config = createConsumerConfig();
        EndpointURIEndpointBuilder endpoint = new EndpointURIEndpointBuilder(SERVICE_ADDRESS, muleContext);
        config.setEndpoint(endpoint);
        config.initialise();
    }

    @Test
    public void createOutboundEndpointBasedOnServiceAddress() throws MuleException
    {
        WSConsumerConfig config = createConsumerConfig();
        OutboundEndpoint outboundEndpoint = config.createOutboundEndpoint();
        assertEquals(SERVICE_ADDRESS, outboundEndpoint.getAddress());
    }

    @Test
    public void createOutboundEndpointBasedOnEndpointRef() throws MuleException
    {
        WSConsumerConfig config = createConsumerConfig();
        config.setServiceAddress(null);
        EndpointURIEndpointBuilder endpoint = new EndpointURIEndpointBuilder(SERVICE_ADDRESS, muleContext);
        config.setEndpoint(endpoint);
        OutboundEndpoint outboundEndpoint = config.createOutboundEndpoint();
        assertEquals(SERVICE_ADDRESS, outboundEndpoint.getAddress());
    }

    private WSConsumerConfig createConsumerConfig()
    {
        WSConsumerConfig config = new WSConsumerConfig();
        config.setMuleContext(muleContext);
        config.setWsdlLocation("TestWsdlLocation");
        config.setServiceAddress(SERVICE_ADDRESS);
        config.setService("TestService");
        config.setPort("TestPort");
        return config;
    }

}
