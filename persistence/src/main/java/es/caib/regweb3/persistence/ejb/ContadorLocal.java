package es.caib.regweb3.persistence.ejb;

import es.caib.regweb3.model.Contador;
import es.caib.regweb3.persistence.utils.NumeroRegistro;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 16/01/14
 */
@Local
@RolesAllowed({"RWE_SUPERADMIN","RWE_ADMIN","RWE_USUARI"})
public interface ContadorLocal extends BaseEjb<Contador, Long> {

    /**
     * Aumenta el 1 el contador pasado por parámetro
     * @param idContador
     * @return
     * @throws Exception
     */
    public NumeroRegistro incrementarContador(Long idContador) throws Exception;

    /**
     * Pone a 0 el Contador
     * @param idContador
     * @throws Exception
     */
    public void reiniciarContador(Long idContador) throws Exception;

}
