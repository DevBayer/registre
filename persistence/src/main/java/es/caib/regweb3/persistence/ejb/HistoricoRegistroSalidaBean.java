package es.caib.regweb3.persistence.ejb;

import es.caib.regweb3.model.*;
import es.caib.regweb3.persistence.utils.RegistroUtils;
import es.caib.regweb3.utils.RegwebConstantes;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author jpernia
 * Date: 30/10/14
 */

@Stateless(name = "HistoricoRegistroSalidaEJB")
@SecurityDomain("seycon")
public class HistoricoRegistroSalidaBean extends BaseEjbJPA<HistoricoRegistroSalida, Long> implements HistoricoRegistroSalidaLocal{

    protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName="regweb3")
    private EntityManager em;


    @Override
    public HistoricoRegistroSalida findById(Long id) throws Exception {

        return em.find(HistoricoRegistroSalida.class, id);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<HistoricoRegistroSalida> getAll() throws Exception {

        return  em.createQuery("Select historicoRegistroSalida from HistoricoRegistroSalida as historicoRegistroSalida order by historicoRegistroSalida.id").getResultList();
    }

    @Override
    public Long getTotal() throws Exception {

        Query q = em.createQuery("Select count(historicoRegistroSalida.id) from HistoricoRegistroSalida as historicoRegistroSalida");

        return (Long) q.getSingleResult();
    }


    @Override
    public List<HistoricoRegistroSalida> getPagination(int inicio) throws Exception {

        Query q = em.createQuery("Select historicoRegistroSalida from HistoricoRegistroSalida as historicoRegistroSalida order by historicoRegistroSalida.id");
        q.setFirstResult(inicio);
        q.setMaxResults(RESULTADOS_PAGINACION);

        return q.getResultList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<HistoricoRegistroSalida> getByRegistroSalida(Long idRegistro) throws Exception {

        Query q = em.createQuery("Select hrs.id, hrs.registroSalidaOriginal, hrs.estado, hrs.fecha, hrs.modificacion, hrs.usuario.id, hrs.usuario.usuario from HistoricoRegistroSalida as hrs where hrs.registroSalida.id =:idRegistro order by hrs.fecha desc");
        q.setParameter("idRegistro", idRegistro);

        List<HistoricoRegistroSalida> hrss = new ArrayList<HistoricoRegistroSalida>();

        List<Object[]> result = q.getResultList();

        for (Object[] object : result) {
            HistoricoRegistroSalida hrs = new HistoricoRegistroSalida((Long) object[0], (String) object[1], (Long) object[2], (Date) object[3], (String) object[4], (Long) object[5], (Usuario) object[6]);

            hrss.add(hrs);
        }

        return hrss;
    }

    @Override
    public List<HistoricoRegistroSalida> salidaModificadaPorUsuario(Date fechaInicio, Date fechaFin, Long idUsuario, List<Libro> libros) throws Exception{

        Query q;

        q = em.createQuery("Select historicoRegistroSalida from HistoricoRegistroSalida as historicoRegistroSalida where historicoRegistroSalida.fecha >= :fechaInicio " +
                "and historicoRegistroSalida.fecha <= :fechaFin and historicoRegistroSalida.usuario.id = :idUsuario and historicoRegistroSalida.modificacion != 'Creación' and historicoRegistroSalida.registroSalida.libro in (:libros) order by historicoRegistroSalida.fecha desc");

        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("idUsuario", idUsuario);
        q.setParameter("libros", libros);

        return q.getResultList();
    }

    @Override
    public List<HistoricoRegistroSalida> salidaModificadaPorUsuarioLibro(Date fechaInicio, Date fechaFin, Long idUsuario, Long idLibro) throws Exception{

        Query q;

        q = em.createQuery("Select historicoRegistroSalida from HistoricoRegistroSalida as historicoRegistroSalida where historicoRegistroSalida.fecha >= :fechaInicio " +
                "and historicoRegistroSalida.fecha <= :fechaFin and historicoRegistroSalida.usuario.id = :idUsuario and historicoRegistroSalida.registroSalida.libro.id = :idLibro and historicoRegistroSalida.modificacion != 'Creación' order by historicoRegistroSalida.fecha desc");

        q.setParameter("fechaInicio", fechaInicio);
        q.setParameter("fechaFin", fechaFin);
        q.setParameter("idUsuario", idUsuario);
        q.setParameter("idLibro", idLibro);

        return q.getResultList();
    }

    @Override
    public HistoricoRegistroSalida crearHistoricoRegistroSalida(RegistroSalida registroSalida, UsuarioEntidad usuarioEntidad, String modificacion, boolean serializar) throws Exception{

        HistoricoRegistroSalida historico = new HistoricoRegistroSalida();

        historico.setEstado(registroSalida.getEstado());
        historico.setRegistroSalida(registroSalida);
        historico.setFecha(new Date());
        historico.setModificacion(modificacion);
        historico.setUsuario(usuarioEntidad);
        //Serializamos el RegistroEntrada original
        if(serializar){
            String registroEntradaOrigial = RegistroUtils.serilizarXml(registroSalida);
            historico.setRegistroSalidaOriginal(registroEntradaOrigial);
        }

        // Guardamos el histórico
        return persist(historico);
    }

    @Override
    public Boolean obtenerPorUsuario(Long idUsuarioEntidad) throws Exception {

        Query q;

        q = em.createQuery("Select count(hrs.id) from HistoricoRegistroSalida as hrs where hrs.usuario.id = :idUsuarioEntidad ");

        q.setParameter("idUsuarioEntidad", idUsuarioEntidad);

        return (Long) q.getSingleResult() > 0;
    }

    @Override
    public Integer eliminarByEntidad(Long idEntidad) throws Exception{

        List<?> hrs = em.createQuery("Select distinct(hre.id) from HistoricoRegistroSalida as hre where hre.registroSalida.usuario.entidad.id =:idEntidad").setParameter("idEntidad",idEntidad).getResultList();
        Integer total = hrs.size();

        if(hrs.size() > 0){

            // Si hay más de 1000 registros, dividimos las queries (ORA-01795).
            while (hrs.size() > RegwebConstantes.NUMBER_EXPRESSIONS_IN) {

                List<?> subList = hrs.subList(0, RegwebConstantes.NUMBER_EXPRESSIONS_IN);
                em.createQuery("delete from HistoricoRegistroSalida where id in (:hrs) ").setParameter("hrs", subList).executeUpdate();
                hrs.subList(0, RegwebConstantes.NUMBER_EXPRESSIONS_IN).clear();
            }

            em.createQuery("delete from HistoricoRegistroSalida where id in (:hrs) ").setParameter("hrs", hrs).executeUpdate();
        }

        return total;
    }


}