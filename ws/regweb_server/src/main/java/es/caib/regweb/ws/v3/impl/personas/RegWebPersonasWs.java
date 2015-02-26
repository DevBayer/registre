package es.caib.regweb.ws.v3.impl.personas;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.fundaciobit.genapp.common.ws.WsI18NException;
import org.fundaciobit.genapp.common.ws.WsValidationException;

import es.caib.regweb.ws.utils.IBaseWs;


/**
 * 
 * @author anadal
 *
 */
@WebService
public interface RegWebPersonasWs extends IBaseWs {

  @WebMethod
  public List<PersonaWs> listarPersonas(@WebParam(name = "entidadCodigoDir3")String entidadCodigoDir3) throws Throwable, WsI18NException;

  @WebMethod
  public Long crearPersona(@WebParam(name = "personaWs")PersonaWs personaWs) throws Throwable, WsI18NException, WsValidationException;

  @WebMethod
  public void borrarPersona(@WebParam(name = "personaID")Long personaID) throws Throwable, WsI18NException;

  @WebMethod
  public void actualizarPersona(@WebParam(name = "personaWs")PersonaWs personaWs) throws Throwable, WsI18NException, WsValidationException;

}