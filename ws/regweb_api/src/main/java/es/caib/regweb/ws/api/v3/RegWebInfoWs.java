package es.caib.regweb.ws.api.v3;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2015-03-20T09:02:43.157+01:00
 * Generated source version: 2.6.4
 * 
 */
@WebService(targetNamespace = "http://impl.v3.ws.regweb.caib.es/", name = "RegWebInfoWs")
@XmlSeeAlso({ObjectFactory.class})
public interface RegWebInfoWs {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarCodigoAsunto", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarCodigoAsunto")
    @WebMethod
    @ResponseWrapper(localName = "listarCodigoAsuntoResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarCodigoAsuntoResponse")
    public java.util.List<es.caib.regweb.ws.api.v3.CodigoAsuntoWs> listarCodigoAsunto(
        @WebParam(name = "usuario", targetNamespace = "")
        java.lang.String usuario,
        @WebParam(name = "codigoTipoAsunto", targetNamespace = "")
        java.lang.String codigoTipoAsunto
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarTipoAsunto", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarTipoAsunto")
    @WebMethod
    @ResponseWrapper(localName = "listarTipoAsuntoResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarTipoAsuntoResponse")
    public java.util.List<es.caib.regweb.ws.api.v3.TipoAsuntoWs> listarTipoAsunto(
        @WebParam(name = "usuario", targetNamespace = "")
        java.lang.String usuario,
        @WebParam(name = "entidadCodigoDir3", targetNamespace = "")
        java.lang.String entidadCodigoDir3
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersion")
    @WebMethod
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionResponse")
    public java.lang.String getVersion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarOrganismos", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarOrganismos")
    @WebMethod
    @ResponseWrapper(localName = "listarOrganismosResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarOrganismosResponse")
    public java.util.List<es.caib.regweb.ws.api.v3.OrganismoWs> listarOrganismos(
        @WebParam(name = "entidadCodigoDir3", targetNamespace = "")
        java.lang.String entidadCodigoDir3
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionWs")
    @WebMethod
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionWsResponse")
    public int getVersionWs();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarLibros", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarLibros")
    @WebMethod
    @ResponseWrapper(localName = "listarLibrosResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarLibrosResponse")
    public java.util.List<es.caib.regweb.ws.api.v3.LibroWs> listarLibros(
        @WebParam(name = "usuario", targetNamespace = "")
        java.lang.String usuario,
        @WebParam(name = "entidadCodigoDir3", targetNamespace = "")
        java.lang.String entidadCodigoDir3,
        @WebParam(name = "autorizacion", targetNamespace = "")
        java.lang.String autorizacion
    ) throws WsI18NException;
}
