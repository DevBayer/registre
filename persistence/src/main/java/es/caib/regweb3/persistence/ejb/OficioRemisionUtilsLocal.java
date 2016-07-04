package es.caib.regweb3.persistence.ejb;


import es.caib.regweb3.model.*;
import es.caib.regweb3.model.utils.OficioPendienteLlegada;
import es.caib.regweb3.persistence.utils.OficiosRemisionInternoOrganismo;
import es.caib.regweb3.persistence.utils.OficiosRemisionOrganismo;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.List;
import java.util.Set;


/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * @author anadal (EJB)
 *         Date: 16/01/14
 */
@Local
@RolesAllowed({"RWE_SUPERADMIN", "RWE_ADMIN", "RWE_USUARI"})
public interface OficioRemisionUtilsLocal {

    /**
     * Obtenemos los Organismos destinatarios PROPIOS que tiene Oficios de Remision pendientes de tramitar
     *
     * @param idLibro
     * @param organismos Lista con los Destinatarios que no se consideran Oficio de Remisión
     * @return
     * @throws Exception
     */
    public List<String> oficiosPendientesRemisionInterna(Long idLibro, Set<Long> organismos) throws Exception;

    /**
     * Busca los RegistroEntrada pendientes de tramitar mediante un OficioRemision INTERNI
     * y los agrupa según su Organismo destinatario.
     *
     * @param any
     * @param idOficina
     * @param idLibro
     * @param organismos Lista con los Destinatarios que no se consideran Oficio de Remisión
     * @return
     * @throws Exception
     */
    public List<OficiosRemisionInternoOrganismo> oficiosPendientesRemisionInterna(Integer any, Long idOficina, Long idLibro, Set<Long> organismos) throws Exception;

    /**
     * Cuenta los Oficios pendientes de Remisión Interna de un conjunto de Libros
     *
     * @param libros
     * @param organismos Lista con los Destinatarios que no se consideran Oficio de Remisión
     * @return
     * @throws Exception
     */
    public Long oficiosPendientesRemisionInternaCount(List<Libro> libros, Set<Long> organismos) throws Exception;


    /**
     * Comprueba si un RegistroEntrada se considera un OficioRemision o no
     *
     * @param idRegistro
     * @param organismos Lista con los Destinatarios que no se consideran Oficio de Remisión
     * @return
     * @throws Exception
     */
    public Boolean isOficioRemisionInterno(Long idRegistro, Set<Long> organismos) throws Exception;

    /**
     * Obtenemos los Organismos destinatarios EXTERNOS que tiene Oficios de Remision pendientes de tramitar
     *
     * @param idLibro
     * @return
     * @throws Exception
     */
    public List<String> oficiosPendientesRemisionExterna(Long idLibro) throws Exception;

    /**
     * Busca los RegistroEntrada pendientes de tramitar mediante un OficioRemision EXTERNO, es decir, cuyo Organismo destino
     * no pertenece a la Entidad Activa y los agrupa según su Organismo destinatario.
     *
     * @param any
     * @param idOficina
     * @param idLibro
     * @param entidadActiva
     * @return
     * @throws Exception
     */
    public List<OficiosRemisionOrganismo> oficiosPendientesRemisionExterna(Integer any, Long idOficina, Long idLibro, Entidad entidadActiva) throws Exception;

    /**
     * Cuenta los Oficios pendientes de Remisión Externa de un conjunto de Libros
     *
     * @param libros
     * @return
     * @throws Exception
     */
    public Long oficiosPendientesRemisionExternaCount(List<Libro> libros) throws Exception;

    /**
     * Busca Oficios de Remisión de un Organismo propio, según los parámetros.
     *
     * @param idOrganismo
     * @param any
     * @param idOficina
     * @param idLibro
     * @return
     * @throws Exception
     */
    public List<RegistroEntrada> oficiosRemisionByOrganismoPropio(Long idOrganismo, Integer any, Long idOficina, Long idLibro) throws Exception;

    /**
     * Busca Oficios de Remisión de un Organismo externo, según los parámetros.
     *
     * @param codigoOrganismo
     * @param any
     * @param idLibro
     * @return
     * @throws Exception
     */
    public List<RegistroEntrada> oficiosRemisionByOrganismoExterno(String codigoOrganismo, Integer any, Long idOficina, Long idLibro) throws Exception;

    /**
     * @param registrosEntrada
     * @param oficinaActiva
     * @param usuarioEntidad
     * @param idOrganismo
     * @param idLibro
     * @return
     * @throws Exception
     * @throws I18NException
     * @throws I18NValidationException
     */
    public OficioRemision crearOficioRemisionInterno(List<RegistroEntrada> registrosEntrada,
                                                     Oficina oficinaActiva, UsuarioEntidad usuarioEntidad,
                                                     Long idOrganismo, Long idLibro) throws Exception, I18NException, I18NValidationException;


    /**
     * @param registrosEntrada
     * @param oficinaActiva
     * @param usuarioEntidad
     * @param organismoExterno
     * @param organismoExternoDenominacion
     * @param idLibro
     * @param identificadorIntercambioSir
     * @return
     * @throws Exception
     * @throws I18NException
     * @throws I18NValidationException
     */
    public OficioRemision crearOficioRemisionExterno(List<RegistroEntrada> registrosEntrada,
                                                     Oficina oficinaActiva, UsuarioEntidad usuarioEntidad, String organismoExterno,
                                                     String organismoExternoDenominacion, Long idLibro,
                                                     String identificadorIntercambioSir) throws Exception, I18NException, I18NValidationException;


    /**
     * @param oficioRemision
     * @param usuario
     * @param oficinaActiva
     * @param oficios
     * @return
     * @throws Exception
     * @throws I18NException
     * @throws I18NValidationException
     */
    public List<RegistroEntrada> procesarOficioRemision(OficioRemision oficioRemision,
                                                        UsuarioEntidad usuario, Oficina oficinaActiva,
                                                        List<OficioPendienteLlegada> oficios) throws Exception, I18NException, I18NValidationException;

}