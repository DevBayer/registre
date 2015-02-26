package es.caib.regweb.webapp.interceptor;

import es.caib.regweb.model.Oficina;
import es.caib.regweb.model.PreRegistro;
import es.caib.regweb.model.Rol;
import es.caib.regweb.persistence.ejb.PreRegistroLocal;
import es.caib.regweb.utils.RegwebConstantes;
import es.caib.regweb.webapp.utils.Mensaje;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Fundació BIT.
 *
 * Interceptor para el Registro de Entrada
 *
 * @author jpernia
 * Date: 5/12/14
 */
public class PreRegistroInterceptor extends HandlerInterceptorAdapter {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = "regweb/PreRegistroEJB/local")
    public PreRegistroLocal preRegistroEjb;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String url = request.getServletPath();
            HttpSession session = request.getSession();
            Rol rolActivo = (Rol) session.getAttribute(RegwebConstantes.SESSION_ROL);
            Oficina oficinaActiva = (Oficina) session.getAttribute(RegwebConstantes.SESSION_OFICINA);
            Boolean tienePreRegistros = (Boolean) session.getAttribute(RegwebConstantes.SESSION_TIENEPREREGISTROS);

            // Comprobamos que el usuario dispone del Rol RWE_USUARI
            if(!rolActivo.getNombre().equals(RegwebConstantes.ROL_USUARI)){
                log.info("Error de rol");
                Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.rol"));
                response.sendRedirect("/regweb/aviso");
                return false;
            }

            // Comprobaciones previas al listado de PreRegistro
            if(url.equals("/preRegistro/list")){

                // Comprobamos que la Oficina tiene PreRegistros
                if(!tienePreRegistros){
                    log.info("Aviso: No hi ha PreRegistres");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.preregistro.list"));
                    response.sendRedirect("/regweb/aviso");
                    return false;
                }

            }

            // Comprobaciones previas al detalle de un PreRegistro
            if(url.contains("detalle")){

                String idPreRegistro =  url.replace("/preRegistro/","").replace("/detalle", ""); //Obtenemos el id a partir de la url
                log.info("idPreRegistro: " + idPreRegistro);

                PreRegistro preRegistro = preRegistroEjb.findById(Long.valueOf(idPreRegistro));

                // Comprobamos que el PreRegistro existe
                if(preRegistro == null){
                    log.info("Aviso: No existeix aquest PreRegistre");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.preregistro.detalle"));
                    response.sendRedirect("/regweb/aviso");
                    return false;
                }

                // Comprobamos que el PreRegistro tiene como destino nuestra Oficina Activa
                if(!preRegistro.getCodigoEntidadRegistralDestino().equals(oficinaActiva.getCodigo())){
                    log.info("Aviso: No és d'aquesta oficina");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.preregistro.detalle"));
                    response.sendRedirect("/regweb/aviso");
                    return false;
                }

            }

            return true;
        } finally {
            //log.info("Interceptor PreRegistro: " + TimeUtils.formatElapsedTime(System.currentTimeMillis() - start));
        }
    }


}