package es.caib.regweb.persistence.ejb;

import es.caib.regweb.model.Libro;

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
public interface LibroLocal extends BaseEjb<Libro, Long> {

    /**
     * Devuelve todos los Libros de relacionados con algún Organismos de la Entidad
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public List<Libro> getLibrosEntidad(Long idEntidad) throws Exception;


    /**
     * Retorna true o false en función de si ya existe algún Libro con ese código
     * @param codigo
     * @param idLibro
     * @return
     * @throws Exception
     */
    public Boolean existeCodigoEdit(String codigo, Long idLibro, Long idEntidad) throws Exception;

    /**
     * Retorna un Libro a partir de su código
     * @param codigo
     * @return
     * @throws Exception
     */
    public Libro findByCodigo(String codigo) throws Exception;

    /**
     * Retorna un Libro a partir de su código y la Entidad a la que pertenece
     * @param codigo
     * @param idEntidad
     * @return
     * @throws Exception
     */
    public Libro findByCodigoEntidad(String codigo, Long idEntidad) throws Exception;

    /**
     * Lista los Libros activos de un Organismo
     * @param idOrganismo
     * @return
     * @throws Exception
     */
    public List<Libro> getLibrosOrganismo(Long idOrganismo) throws Exception;

}