package es.caib.regweb3.persistence.ejb;

import es.caib.regweb3.model.TipoDocumental;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 16/01/14
 */
@Local
@RolesAllowed({"RWE_SUPERADMIN","RWE_ADMIN","RWE_USUARI"})
public interface TipoDocumentalLocal extends BaseEjb<TipoDocumental, Long> {

    /**
     *
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Long getTotal(Long idEntidad) throws Exception;

    /**
     *
     * @param inicio
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public List<TipoDocumental> getPagination(int inicio, Long idEntidad) throws Exception;

    /**
     * Retorna el {@link es.caib.regweb3.model.TipoDocumental} asociado a un codigo.
     * @param codigoNTI
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public TipoDocumental findByCodigoEntidad(String codigoNTI,Long idEntidad) throws Exception;

    /**
     * Comprueba la existencia del codigo en algún TipoDocumental
     * @param codigoNTI
     * @param idTipoDocumental
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Boolean existeCodigoEdit(String codigoNTI, Long idTipoDocumental, Long idEntidad) throws Exception;

    /**
     *  Obtiene el tipoDocumental asociado al codigo NTI indicado
     * @param codigo
     * @return
     * @throws Exception
     */
    public TipoDocumental findByCodigoNTI(String codigo) throws Exception;

  /**
   * Obtiene los tipos documentales de una entidad
   * @param idEntidad
   * @return
   * @throws Exception
   */
    public List<TipoDocumental> getByEntidad(Long idEntidad) throws Exception;

    /**
     * Elimina los TipoDocumental de una Entidad
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Integer eliminarByEntidad(Long idEntidad) throws Exception;


}
