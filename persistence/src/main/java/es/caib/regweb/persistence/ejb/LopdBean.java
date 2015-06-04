package es.caib.regweb.persistence.ejb;

import es.caib.regweb.model.*;
import es.caib.regweb.persistence.utils.Paginacion;
import es.caib.regweb.utils.RegwebConstantes;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Fundació BIT.
 *
 * @author jpernia
 * Date: 02/10/14
 */

@Stateless(name = "LopdEJB")
@SecurityDomain("seycon")
public class LopdBean extends BaseEjbJPA<Lopd, Long> implements LopdLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="regweb")
    private EntityManager em;

    @EJB(mappedName = "regweb/RegistroEntradaEJB/local")
    public RegistroEntradaLocal registroEntradaEjb;

    @EJB(mappedName = "regweb/RegistroSalidaEJB/local")
    public RegistroSalidaLocal registroSalidaEjb;

    @EJB(mappedName = "regweb/UsuarioEntidadEJB/local")
    public UsuarioEntidadLocal usuarioEntidadEjb;

    @Override
    public Lopd findById(Long id) throws Exception {

        return em.find(Lopd.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Lopd> getAll() throws Exception {

        return  em.createQuery("Select lopd from Lopd as lopd order by lopd.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(lopd.id) from Lopd as lopd");

        return (Long) q.getSingleResult();
    }


    @Override
    public List<Lopd> getByFechasUsuario(Date fechaInicio, Date fechaFin, Long idUsuarioEntidad, List<Libro> libros, Long accion, Long tipoRegistro) throws Exception {

        Query q = em.createQuery("Select lopd from Lopd as lopd where lopd.usuario.id = :idUsuarioEntidad and " +
                "lopd.fecha >= :fechaInicio and lopd.fecha <= :fechaFin and lopd.accion = :accion and " +
                "lopd.tipoRegistro = :tipoRegistro and lopd.libro in (:libros) order by lopd.fecha desc");

        q.setParameter("idUsuarioEntidad",idUsuarioEntidad);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("libros", libros);
        q.setParameter("accion", accion);
        q.setParameter("tipoRegistro", tipoRegistro);

        return q.getResultList();
    }

    @Override
    public List<Lopd> getByFechasUsuarioLibro(Date fechaInicio, Date fechaFin, Long idUsuarioEntidad, Long idLibro, Long accion, Long tipoRegistro) throws Exception {

        Query q = em.createQuery("Select lopd from Lopd as lopd where lopd.usuario.id = :idUsuarioEntidad and " +
                "lopd.fecha >= :fechaInicio and lopd.fecha <= :fechaFin and lopd.accion = :accion and " +
                "lopd.tipoRegistro = :tipoRegistro and lopd.libro.id = :idLibro order by lopd.fecha desc");

        q.setParameter("idUsuarioEntidad",idUsuarioEntidad);
        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("idLibro", idLibro);
        q.setParameter("accion", accion);
        q.setParameter("tipoRegistro", tipoRegistro);

        return q.getResultList();
    }

    @Override
    public List<Lopd> getByRegistro(String anyoRegistro, Integer numRegistro, Long idLibro, Long accion, Long tipoRegistro) throws Exception {

        Query q = em.createQuery("Select lopd from Lopd as lopd where lopd.anyoRegistro = :anyoRegistro and " +
                "lopd.accion = :accion and lopd.numeroRegistro = :numRegistro and " +
                "lopd.tipoRegistro = :tipoRegistro and lopd.libro.id = :idLibro order by lopd.fecha desc");

        q.setParameter("anyoRegistro", anyoRegistro);
        q.setParameter("numRegistro", numRegistro);
        q.setParameter("idLibro", idLibro);
        q.setParameter("accion", accion);
        q.setParameter("tipoRegistro", tipoRegistro);

        return q.getResultList();
    }

    @Override
    public List<Lopd> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select lopd from Lopd as lopd order by lopd.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    public void insertarRegistroEntrada(Long idRegistro, Long idUsuarioEntidad) throws Exception{

        RegistroEntrada registroEntrada =registroEntradaEjb.findById(idRegistro);
        UsuarioEntidad usuarioEntidad = usuarioEntidadEjb.findById(idUsuarioEntidad);
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        Lopd lopd = new Lopd();
        lopd.setNumeroRegistro(registroEntrada.getNumeroRegistro());
        lopd.setTipoRegistro(RegwebConstantes.REGISTRO_ENTRADA);
        lopd.setAnyoRegistro(formatYear.format(registroEntrada.getFecha()));
        lopd.setLibro(registroEntrada.getLibro());
        lopd.setFecha(Calendar.getInstance().getTime());
        lopd.setUsuario(usuarioEntidad);
        lopd.setAccion(RegwebConstantes.LOPD_CONSULTA);

        persist(lopd);
    }

    @Override
    public void insertarRegistrosEntrada(Paginacion paginacion, Long idUsuarioEntidad) throws Exception{

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        UsuarioEntidad usuarioEntidad = usuarioEntidadEjb.findById(idUsuarioEntidad);

        for (int i = 0; i<paginacion.getListado().size(); i++){
            RegistroEntrada registro = (RegistroEntrada) paginacion.getListado().get(i);
            Lopd lopd = new Lopd();
            lopd.setNumeroRegistro(registro.getNumeroRegistro());
            lopd.setTipoRegistro(RegwebConstantes.REGISTRO_ENTRADA);
            lopd.setAnyoRegistro(formatYear.format(registro.getFecha()));
            lopd.setLibro(registro.getLibro());
            lopd.setFecha(Calendar.getInstance().getTime());
            lopd.setUsuario(usuarioEntidad);
            lopd.setAccion(RegwebConstantes.LOPD_LISTADO);

            persist(lopd);
        }
    }

    @Override
    public void insertarRegistroSalida(Long idRegistro, Long idUsuarioEntidad) throws Exception{

        RegistroSalida registroSalida = registroSalidaEjb.findById(idRegistro);
        UsuarioEntidad usuarioEntidad = usuarioEntidadEjb.findById(idUsuarioEntidad);
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        Lopd lopd = new Lopd();
        lopd.setNumeroRegistro(registroSalida.getNumeroRegistro());
        lopd.setTipoRegistro(RegwebConstantes.REGISTRO_SALIDA);
        lopd.setAnyoRegistro(formatYear.format(registroSalida.getFecha()));
        lopd.setLibro(registroSalida.getLibro());
        lopd.setFecha(Calendar.getInstance().getTime());
        lopd.setUsuario(usuarioEntidad);
        lopd.setAccion(RegwebConstantes.LOPD_CONSULTA);

        persist(lopd);
    }

    @Override
    public void insertarRegistrosSalida(Paginacion paginacion, Long idUsuarioEntidad) throws Exception{

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        UsuarioEntidad usuarioEntidad = usuarioEntidadEjb.findById(idUsuarioEntidad);

        for (int i = 0; i<paginacion.getListado().size(); i++){
            RegistroSalida registro = (RegistroSalida) paginacion.getListado().get(i);
            Lopd lopd = new Lopd();
            lopd.setNumeroRegistro(registro.getNumeroRegistro());
            lopd.setTipoRegistro(RegwebConstantes.REGISTRO_SALIDA);
            lopd.setAnyoRegistro(formatYear.format(registro.getFecha()));
            lopd.setLibro(registro.getLibro());
            lopd.setFecha(Calendar.getInstance().getTime());
            lopd.setUsuario(usuarioEntidad);
            lopd.setAccion(RegwebConstantes.LOPD_LISTADO);

            persist(lopd);
        }
    }

    @Override
    public Integer eliminarByEntidad(Long idEntidad) throws Exception{

        List lopd =  em.createQuery("select distinct(l.id) from Lopd as l where l.usuario.entidad.id =:idEntidad").setParameter("idEntidad",idEntidad).getResultList();

        if(lopd.size() > 0){
            return em.createQuery("delete from Lopd where id in (:lopd)").setParameter("lopd", lopd).executeUpdate();
        }
        return 0;

    }

}
