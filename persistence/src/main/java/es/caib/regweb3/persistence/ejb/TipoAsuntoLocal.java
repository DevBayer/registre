package es.caib.regweb3.persistence.ejb;

import es.caib.regweb3.model.TipoAsunto;

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
public interface TipoAsuntoLocal extends BaseEjb<TipoAsunto, Long> {

    /**
     *
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Long getTotal(Long idEntidad) throws Exception;
    
    /**
     * 
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public List<TipoAsunto> getAll(Long idEntidad) throws Exception;

    /**
     *
     * @param inicio
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public List<TipoAsunto> getPagination(int inicio, Long idEntidad) throws Exception;

    /**
    * Método que obtiene los tipos asuntos que están activos.
    * Se usa a la hora de crear un registro de entrada.
    * @return
    * @throws Exception
    */
    public List<TipoAsunto> getActivosEntidad(Long idEntidad) throws Exception;

    /**
     * Comprueba la existencia del codigo en algún TipoAsunto
     * @param codigo
     * @param idTipoAsunto
     * @return
     * @throws Exception
     */
    public Boolean existeCodigoEdit(String codigo, Long idTipoAsunto,Long idEntidad) throws Exception;

    /**
     * Retorna el {@link es.caib.regweb3.model.TipoAsunto} asociado a un codigo.
     * @param codigo
     * @return
     * @throws Exception
     */
    public TipoAsunto findByCodigo(String codigo) throws Exception;

    /**
     * Retorna el {@link es.caib.regweb3.model.TipoAsunto} asociado a un codigo y a una Entidad
     * @param codigo
     * @return
     * @throws Exception
     */
    public TipoAsunto findByCodigoEntidad(String codigo,Long idEntidad) throws Exception;

    /**
     * Obtiene el Total de {@link es.caib.regweb3.model.TipoAsunto} asociado a una Entidad
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Long getTotalEntidad(Long idEntidad) throws Exception;

    /**
     * Elimina los {@link es.caib.regweb3.model.TipoAsunto} de una Entidad
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Integer eliminarByEntidad(Long idEntidad) throws Exception;
}
