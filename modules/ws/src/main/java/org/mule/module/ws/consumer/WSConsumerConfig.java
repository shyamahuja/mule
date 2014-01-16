/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.ws.consumer;


import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.context.MuleContextAware;
import org.mule.api.endpoint.EndpointBuilder;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.i18n.CoreMessages;
import org.mule.module.ws.security.WSSecurity;
import org.mule.util.StringUtils;

public class WSConsumerConfig implements Initialisable, MuleContextAware
{

    private MuleContext muleContext;
    private String name;
    private String wsdlLocation;
    private String service;
    private String port;
    private String serviceAddress;
    private EndpointBuilder endpoint;
    private WSSecurity security;

    @Override
    public void initialise() throws InitialisationException
    {
        if (StringUtils.isEmpty(serviceAddress) && endpoint == null)
        {
            throw new InitialisationException(CoreMessages.createStaticMessage("Either serviceAddress or endpoint-ref should " +
                                                                               "be set in the configuration element."), this);
        }

        if (!StringUtils.isEmpty(serviceAddress) && endpoint != null)
        {
            throw new InitialisationException(CoreMessages.createStaticMessage("Cannot set both serviceAddress and endpoint-ref in " +
                                                                               "the consumer-config element. Only one must be specified."), this);
        }
    }

    @Override
    public void setMuleContext(MuleContext muleContext)
    {
        this.muleContext = muleContext;
    }

    public OutboundEndpoint createOutboundEndpoint() throws MuleException
    {
        if (endpoint != null)
        {
            return endpoint.buildOutboundEndpoint();
        }

        EndpointBuilder builder = muleContext.getEndpointFactory().getEndpointBuilder(serviceAddress);
        return muleContext.getEndpointFactory().getOutboundEndpoint(builder);
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getWsdlLocation()
    {
        return wsdlLocation;
    }

    public void setWsdlLocation(String wsdlLocation)
    {
        this.wsdlLocation = wsdlLocation;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getServiceAddress()
    {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress)
    {
        this.serviceAddress = serviceAddress;
    }

    public EndpointBuilder getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(EndpointBuilder endpoint)
    {
        this.endpoint = endpoint;
    }

    public WSSecurity getSecurity()
    {
        return security;
    }

    public void setSecurity(WSSecurity security)
    {
        this.security = security;
    }

}
