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
 * 2015-03-20T11:14:57.022+01:00
 * Generated source version: 2.6.4
 * 
 */
@WebService(targetNamespace = "http://impl.v3.ws.regweb.caib.es/", name = "RegWebPersonasWs")
@XmlSeeAlso({ObjectFactory.class})
public interface RegWebPersonasWs {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "crearPersona", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.CrearPersona")
    @WebMethod
    @ResponseWrapper(localName = "crearPersonaResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.CrearPersonaResponse")
    public java.lang.Long crearPersona(
        @WebParam(name = "personaWs", targetNamespace = "")
        es.caib.regweb.ws.api.v3.PersonaWs personaWs
    ) throws WsValidationException, WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "listarPersonas", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarPersonas")
    @WebMethod
    @ResponseWrapper(localName = "listarPersonasResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ListarPersonasResponse")
    public java.util.List<es.caib.regweb.ws.api.v3.PersonaWs> listarPersonas(
        @WebParam(name = "entidadCodigoDir3", targetNamespace = "")
        java.lang.String entidadCodigoDir3
    ) throws WsI18NException;

    @RequestWrapper(localName = "borrarPersona", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.BorrarPersona")
    @WebMethod
    @ResponseWrapper(localName = "borrarPersonaResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.BorrarPersonaResponse")
    public void borrarPersona(
        @WebParam(name = "personaID", targetNamespace = "")
        java.lang.Long personaID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersion")
    @WebMethod
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionResponse")
    public java.lang.String getVersion();

    @RequestWrapper(localName = "actualizarPersona", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ActualizarPersona")
    @WebMethod
    @ResponseWrapper(localName = "actualizarPersonaResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.ActualizarPersonaResponse")
    public void actualizarPersona(
        @WebParam(name = "personaWs", targetNamespace = "")
        es.caib.regweb.ws.api.v3.PersonaWs personaWs
    ) throws WsValidationException, WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionWs")
    @WebMethod
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://impl.v3.ws.regweb.caib.es/", className = "es.caib.regweb.ws.api.v3.GetVersionWsResponse")
    public int getVersionWs();
}
