package es.caib.regweb.ws.api.v3;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.2.12-patch-04
 * Mon Dec 15 12:12:32 CET 2014
 * Generated source version: 2.2.12-patch-04
 * 
 */
 
@WebService(targetNamespace = "http://impl.v3.ws.regweb.caib.es/", name = "RegWebHelloWorldWithSecurityWs")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RegWebHelloWorldWithSecurityWs {

    @WebResult(name = "return", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", partName = "return")
    @WebMethod
    public java.lang.String echo(
        @WebParam(partName = "echo", name = "echo")
        java.lang.String echo
    );

    @WebResult(name = "return", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", partName = "return")
    @WebMethod
    public java.lang.String getVersion();

    @WebResult(name = "return", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", partName = "return")
    @WebMethod
    public int getVersionWs();
}
