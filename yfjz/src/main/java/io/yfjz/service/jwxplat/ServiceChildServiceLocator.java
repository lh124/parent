/**
 * ServiceChildServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

import io.yfjz.utils.PropertiesUtils;

public class ServiceChildServiceLocator extends org.apache.axis.client.Service implements io.yfjz.service.jwxplat.ServiceChildService {

    public ServiceChildServiceLocator() {
    }


    public ServiceChildServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceChildServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ChildService
    private java.lang.String ChildService_address = PropertiesUtils.getMapValue("PLATSERVICE_URL");
            /*"http://222.85.149.105:7001/vaccine/services/ChildService";*/

    @Override
    public java.lang.String getChildServiceAddress() {
        return ChildService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ChildServiceWSDDServiceName = "ChildService";

    public java.lang.String getChildServiceWSDDServiceName() {
        return ChildServiceWSDDServiceName;
    }

    public void setChildServiceWSDDServiceName(java.lang.String name) {
        ChildServiceWSDDServiceName = name;
    }

    @Override
    public io.yfjz.service.jwxplat.ServiceChild getChildService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ChildService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getChildService(endpoint);
    }

    public io.yfjz.service.jwxplat.ServiceChild getChildService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            io.yfjz.service.jwxplat.ChildServiceSoapBindingStub _stub = new io.yfjz.service.jwxplat.ChildServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getChildServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setChildServiceEndpointAddress(java.lang.String address) {
        ChildService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (io.yfjz.service.jwxplat.ServiceChild.class.isAssignableFrom(serviceEndpointInterface)) {
                io.yfjz.service.jwxplat.ChildServiceSoapBindingStub _stub = new io.yfjz.service.jwxplat.ChildServiceSoapBindingStub(new java.net.URL(ChildService_address), this);
                _stub.setPortName(getChildServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ChildService".equals(inputPortName)) {
            return getChildService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://222.85.149.105:7001/vaccine/services/ChildService", "ServiceChildService");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://222.85.149.105:7001/vaccine/services/ChildService", "ChildService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ChildService".equals(portName)) {
            setChildServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
