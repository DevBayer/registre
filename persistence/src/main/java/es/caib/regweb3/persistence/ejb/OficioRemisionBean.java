package es.caib.regweb3.persistence.ejb;

import es.caib.regweb3.model.*;
import es.caib.regweb3.persistence.utils.NumeroRegistro;
import es.caib.regweb3.persistence.utils.Paginacion;
import es.caib.regweb3.utils.RegwebConstantes;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 * Date: 16/01/14
 */

@Stateless(name = "OficioRemisionEJB")
@SecurityDomain("seycon")
public class OficioRemisionBean extends BaseEjbJPA<OficioRemision, Long> implements OficioRemisionLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="regweb3")
    private EntityManager em;

    @EJB(mappedName = "regweb3/LibroEJB/local")
    public LibroLocal libroEjb;

    @EJB(mappedName = "regweb3/RegistroSalidaEJB/local")
    public RegistroSalidaLocal registroSalidaEjb;

    @EJB(mappedName = "regweb3/RegistroEntradaEJB/local")
    public RegistroEntradaLocal registroEntradaEjb;

    @EJB(mappedName = "regweb3/HistoricoRegistroEntradaEJB/local")
    public HistoricoRegistroEntradaLocal historicoRegistroEntradaEjb;

    @EJB(mappedName = "regweb3/HistoricoRegistroSalidaEJB/local")
    public HistoricoRegistroSalidaLocal historicoRegistroSalidaEjb;

    @EJB(mappedName = "regweb3/TrazabilidadEJB/local")
    public TrazabilidadLocal trazabilidadEjb;

    @EJB(mappedName = "regweb3/ContadorEJB/local")
    public ContadorLocal contadorEjb;


    @Override
    public OficioRemision findById(Long id) throws Exception {

        return em.find(OficioRemision.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<OficioRemision> getAll() throws Exception {

        return  em.createQuery("Select oficioRemision from OficioRemision as oficioRemision order by oficioRemision.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(oficioRemision.id) from OficioRemision as oficioRemision");

        return (Long) q.getSingleResult();
    }


    @Override
    @SuppressWarnings(value = "unchecked")
    public List<OficioRemision> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select oficioRemision from OficioRemision as oficioRemision order by oficioRemision.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public Paginacion busqueda(Integer pageNumber, Integer any, OficioRemision oficioRemision, List<Libro> libros, Long tipoOficioRemision, Integer estadoOficioRemision) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuffer query = new StringBuffer("Select oficioRemision from OficioRemision as oficioRemision ");


        if(oficioRemision.getNumeroOficio()!= null && oficioRemision.getNumeroOficio() > 0){where.add(" oficioRemision.numeroOficio = :numeroOficio"); parametros.put("numeroOficio",oficioRemision.getNumeroOficio());}

        if(any!= null){where.add(" year(oficioRemision.fecha) = :any "); parametros.put("any",any);}

        // Comprobamos si la búsqueda es sobre un libro en concreto o sobre todos a los que tiene acceso el usuario.
        if(oficioRemision.getLibro().getId() != null && oficioRemision.getLibro().getId() > 0){
            where.add(" oficioRemision.libro.id = :idLibro"); parametros.put("idLibro",oficioRemision.getLibro().getId());
        }else{
            where.add(" oficioRemision.libro in (:libros)"); parametros.put("libros",libros);
        }

        // Tipo Oficio Remisión Interno o Externo
        if (tipoOficioRemision != null) {
            if (tipoOficioRemision.equals(RegwebConstantes.TIPO_OFICIO_REMISION_INTERNO)) {
                where.add(" oficioRemision.organismoDestinatario != null");
            } else if (tipoOficioRemision.equals(RegwebConstantes.TIPO_OFICIO_REMISION_EXTERNO)) {
                where.add(" oficioRemision.organismoDestinatario is null");
            }
        }

        // Estado Oficio Remisión
        if(estadoOficioRemision != null){
            where.add(" oficioRemision.estado = :estadoOficioRemision");
            parametros.put("estadoOficioRemision",estadoOficioRemision);
        }

        if (parametros.size() != 0) {
            query.append("where ");
            int count = 0;
            for (String w : where) {
                if (count != 0) {
                    query.append(" and ");
                }
                query.append(w);
                count++;
            }
            q2 = em.createQuery(query.toString().replaceAll("Select oficioRemision from OficioRemision as oficioRemision ", "Select count(oficioRemision.id) from OficioRemision as oficioRemision "));
            query.append(" order by oficioRemision.id desc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {

                q.setParameter(param.getKey(), param.getValue());
                q2.setParameter(param.getKey(), param.getValue());
            }

        }else{
            q2 = em.createQuery(query.toString().replaceAll("Select oficioRemision from OficioRemision as oficioRemision ", "Select count(oficioRemision.id) from OficioRemision as oficioRemision "));
            query.append("order by oficioRemision.id desc");
            q = em.createQuery(query.toString());
        }


        Paginacion paginacion = null;

        if(pageNumber != null){ // Comprobamos si es una busqueda paginada o no
            Long total = (Long)q2.getSingleResult();
            paginacion = new Paginacion(total.intValue(), pageNumber);
            int inicio = (pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION;
            q.setFirstResult(inicio);
            q.setMaxResults(RESULTADOS_PAGINACION);
        }else{
            paginacion = new Paginacion(0, 0);
        }

        paginacion.setListado(q.getResultList());

        return paginacion;
    }


    @Override    
    public synchronized OficioRemision registrarOficioRemision(OficioRemision oficioRemision,
        Long estado) throws Exception, I18NException, I18NValidationException {

        // Obtenemos el Número de registro del OficioRemision
        Libro libro = libroEjb.findById(oficioRemision.getLibro().getId());
        NumeroRegistro numeroRegistro = contadorEjb.incrementarContador(libro.getContadorOficioRemision().getId());
        oficioRemision.setNumeroOficio(numeroRegistro.getNumero());
        oficioRemision.setFecha(numeroRegistro.getFecha());

        // Guardamos el Oficio de Remisión
        oficioRemision = persist(oficioRemision);

        // Creamos un Registro de Salida por cada Registro de Entrada que contenga el OficioRemision
        for (RegistroEntrada registroEntrada : oficioRemision.getRegistrosEntrada()) {
            RegistroSalida registroSalida = new RegistroSalida();

            registroSalida.setUsuario(oficioRemision.getUsuarioResponsable());
            registroSalida.setOficina(oficioRemision.getOficina());
            registroSalida.setOrigen(oficioRemision.getLibro().getOrganismo());//todo: Esta asignación es correcta?
            registroSalida.setLibro(oficioRemision.getLibro());
            registroSalida.setRegistroDetalle(registroEntrada.getRegistroDetalle());
            registroSalida.setEstado(RegwebConstantes.REGISTRO_TRAMITADO);

            // Registramos la Salida
            registroSalida = registroSalidaEjb.registrarSalida(registroSalida, oficioRemision.getUsuarioResponsable());

            // CREAMOS LA TRAZABILIDAD
            Trazabilidad trazabilidad = new Trazabilidad();
            trazabilidad.setRegistroEntradaOrigen(registroEntrada);
            trazabilidad.setOficioRemision(oficioRemision);
            trazabilidad.setRegistroSalida(registroSalida);
            trazabilidad.setFecha(new Date());

            trazabilidadEjb.persist(trazabilidad);

            // Modificamos el estado del Registro de Entrada
            registroEntradaEjb.cambiarEstado(registroEntrada,estado, oficioRemision.getUsuarioResponsable());
        }


        return oficioRemision;
    }

    @Override
    public void anularOficioRemisionInterno(Long idOficioRemision, UsuarioEntidad usuarioEntidad) throws Exception{

        OficioRemision oficioRemision = findById(idOficioRemision);
        List<RegistroEntrada> registrosEntrada = getByOficioRemision(oficioRemision.getId());

        // Modificamos el estado de cada RE a Válido
        for(RegistroEntrada registroEntrada:registrosEntrada){
            registroEntradaEjb.cambiarEstado(registroEntrada,RegwebConstantes.REGISTRO_VALIDO, usuarioEntidad);
        }

        // Anulamos los Registros de Salida generado por el Oficio
        for(RegistroSalida registroSalida:trazabilidadEjb.obtenerRegistrosSalida(idOficioRemision)){
            registroSalidaEjb.cambiarEstado(registroSalida,RegwebConstantes.REGISTRO_ANULADO, usuarioEntidad);
        }

        // Anulamos el Oficio de Remisión
        oficioRemision.setEstado(RegwebConstantes.OFICIO_REMISION_ANULADO);
        merge(oficioRemision);

    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<OficioRemision> oficiosPendientesLlegada(Set<Organismo> organismos, Integer total) throws Exception {

        Query q = em.createQuery("Select oficioRemision from OficioRemision as oficioRemision "
                + "where oficioRemision.organismoDestinatario in (:organismos) "
                + " and oficioRemision.estado = " + RegwebConstantes.OFICIO_REMISION_INTERNO_ENVIADO
                + " order by oficioRemision.id desc");

        q.setParameter("organismos",organismos);
        if(total != null){
            q.setMaxResults(total);
        }

        return q.getResultList();
    }

    @Override
    public Paginacion oficiosPendientesLlegadaBusqueda(Set<Organismo> organismos, Integer pageNumber,OficioRemision oficioRemision, List<Libro> libros) throws Exception {

        Query q;
        Query q2;
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<String> where = new ArrayList<String>();

        StringBuffer query = new StringBuffer("Select oficioRemision from OficioRemision as oficioRemision ");

        where.add(" oficioRemision.organismoDestinatario in (:organismos)"); parametros.put("organismos",organismos);
        where.add(" oficioRemision.estado = :estado");parametros.put("estado",RegwebConstantes.OFICIO_REMISION_INTERNO_ENVIADO);

        if(oficioRemision.getNumeroOficio()!= null && oficioRemision.getNumeroOficio() > 0){where.add(" oficioRemision.numeroOficio = :numeroOficio"); parametros.put("numeroOficio",oficioRemision.getNumeroOficio());}

        // Comprobamos si la búsqueda es sobre un libro en concreto o sobre todos a los que tiene acceso el usuario.
        if(oficioRemision.getLibro().getId() != null && oficioRemision.getLibro().getId() > 0){
            where.add(" oficioRemision.libro.id = :idLibro"); parametros.put("idLibro",oficioRemision.getLibro().getId());
        }else{
            where.add(" oficioRemision.libro in (:libros)"); parametros.put("libros",libros);
        }


        if (parametros.size() != 0) {
            query.append("where ");
            int count = 0;
            for (String w : where) {
                if (count != 0) {
                    query.append(" and ");
                }
                query.append(w);
                count++;
            }
            q2 = em.createQuery(query.toString().replaceAll("Select oficioRemision from OficioRemision as oficioRemision ", "Select count(oficioRemision.id) from OficioRemision as oficioRemision "));
            query.append(" order by oficioRemision.id desc");
            q = em.createQuery(query.toString());

            for (Map.Entry<String, Object> param : parametros.entrySet()) {

                q.setParameter(param.getKey(), param.getValue());
                q2.setParameter(param.getKey(), param.getValue());
            }

        }else{
            q2 = em.createQuery(query.toString().replaceAll("Select oficioRemision from OficioRemision as oficioRemision ", "Select count(oficioRemision.id) from OficioRemision as oficioRemision "));
            query.append("order by oficioRemision.id desc");
            q = em.createQuery(query.toString());
        }


        Paginacion paginacion = null;

        if(pageNumber != null){ // Comprobamos si es una busqueda paginada o no
            Long total = (Long)q2.getSingleResult();
            paginacion = new Paginacion(total.intValue(), pageNumber);
            int inicio = (pageNumber - 1) * BaseEjbJPA.RESULTADOS_PAGINACION;
            q.setFirstResult(inicio);
            q.setMaxResults(RESULTADOS_PAGINACION);
        }else{
            paginacion = new Paginacion(0, 0);
        }

        paginacion.setListado(q.getResultList());

        return paginacion;
    }

    @Override
    public Long oficiosPendientesLlegadaCount(Set<Organismo> organismos) throws Exception {

        Query q = em.createQuery("Select count(oficioRemision.id) from OficioRemision as oficioRemision "
                + "where oficioRemision.organismoDestinatario in (:organismos) "
                + " and oficioRemision.estado = " + RegwebConstantes.OFICIO_REMISION_INTERNO_ENVIADO);

        q.setParameter("organismos",organismos);

        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<RegistroEntrada> getByOficioRemision(Long idOficioRemision) throws Exception{

        Query q = em.createQuery("Select oficioRemision.registrosEntrada from OficioRemision as oficioRemision where oficioRemision.id = :idOficioRemision ");

        q.setParameter("idOficioRemision", idOficioRemision);

        return q.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Integer eliminarByEntidad(Long idEntidad) throws Exception{

        Query or = em.createQuery("select distinct(id) from OficioRemision where usuarioResponsable.entidad.id = :idEntidad");
        or.setParameter("idEntidad", idEntidad);
        List<Object> oficiosRemision =  or.getResultList();

        for (Object id : oficiosRemision) {
            remove(findById((Long) id));
        }

        return oficiosRemision.size();

    }
}