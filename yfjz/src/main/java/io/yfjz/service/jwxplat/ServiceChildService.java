/**
 * ServiceChildService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

public interface ServiceChildService extends javax.xml.rpc.Service {
    public java.lang.String getChildServiceAddress();

    public io.yfjz.service.jwxplat.ServiceChild getChildService() throws javax.xml.rpc.ServiceException;

    public io.yfjz.service.jwxplat.ServiceChild getChildService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
