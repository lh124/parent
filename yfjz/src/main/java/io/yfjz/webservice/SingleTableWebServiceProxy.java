package io.yfjz.webservice;

import org.springframework.stereotype.Service;

@Service("singleTableWebService")
public class SingleTableWebServiceProxy implements SingleTableWebService {
  private String _endpoint = null;
  private SingleTableWebService singleTableWebService = null;
  
  public SingleTableWebServiceProxy() {
    _initSingleTableWebServiceProxy();
  }
  
  public SingleTableWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initSingleTableWebServiceProxy();
  }
  
  private void _initSingleTableWebServiceProxy() {
    try {
      singleTableWebService = (new io.yfjz.webservice.impl.SingleTableWebServiceImplServiceLocator()).getSingleTableWebServiceImplPort();
      if (singleTableWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)singleTableWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)singleTableWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (singleTableWebService != null)
      ((javax.xml.rpc.Stub)singleTableWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public SingleTableWebService getSingleTableWebService() {
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService;
  }
  
  public String findObjectsByParams(String arg0, String arg1, String arg2, String arg3) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.findObjectsByParams(arg0, arg1, arg2, arg3);
  }
  
  public String acknowledge(String arg0, String[] arg1, String arg2) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.acknowledge(arg0, arg1, arg2);
  }
  
  public String findAllObjectsMultiUpdate(String arg0, String arg1) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.findAllObjectsMultiUpdate(arg0, arg1);
  }
  
  public String findAllUpdate(String arg0, String arg1) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.findAllUpdate(arg0, arg1);
  }
  
  public String findAllNeedUpdate(String arg0, String arg1) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.findAllNeedUpdate(arg0, arg1);
  }
  
  public String acknowledgeByPlatform(String arg0, String arg1) throws java.rmi.RemoteException{
    if (singleTableWebService == null)
      _initSingleTableWebServiceProxy();
    return singleTableWebService.acknowledgeByPlatform(arg0, arg1);
  }
  
  
}