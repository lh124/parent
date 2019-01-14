/**
 * SingleTableWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.webservice;

public interface SingleTableWebService extends java.rmi.Remote {
    public String findObjectsByParams(String arg0, String arg1, String arg2, String arg3) throws java.rmi.RemoteException;
    public String acknowledge(String arg0, String[] arg1, String arg2) throws java.rmi.RemoteException;
    public String findAllObjectsMultiUpdate(String arg0, String arg1) throws java.rmi.RemoteException;
    public String findAllUpdate(String arg0, String arg1) throws java.rmi.RemoteException;
    public String findAllNeedUpdate(String arg0, String arg1) throws java.rmi.RemoteException;
    public String acknowledgeByPlatform(String arg0, String arg1) throws java.rmi.RemoteException;
}
