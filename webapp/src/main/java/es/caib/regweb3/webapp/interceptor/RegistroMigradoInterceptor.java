package es.caib.regweb3.webapp.interceptor;

import es.caib.regweb3.model.Rol;
import es.caib.regweb3.utils.RegwebConstantes;
import es.caib.regweb3.webapp.utils.Mensaje;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Fundació BIT.
 *
 * Interceptor para los Registros Migrados
 *
 * @author jpernia
 * Date: 5/12/14
 */
public class RegistroMigradoInterceptor extends HandlerInterceptorAdapter {

    protected final Logger log = Logger.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String url = request.getServletPath();
            HttpSession session = request.getSession();
            Rol rolActivo = (Rol) session.getAttribute(RegwebConstantes.SESSION_ROL);
            Boolean tieneRegistrosMigrados = (Boolean) session.getAttribute(RegwebConstantes.SESSION_MIGRADOS);

            // Comprobamos que el usuario dispone del Rol RWE_USUARI o Rol RWE_ADMIN
            if(!(rolActivo.getNombre().equals(RegwebConstantes.ROL_USUARI)||rolActivo.getNombre().equals(RegwebConstantes.ROL_ADMIN))){
                log.info("Error de rol");
                Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.rol"));
                response.sendRedirect("/regweb3/aviso");
                return false;
            }

            // Comprobaciones previas al listado de Registro Migrado
            if(url.equals("/registroMigrado/list")){
                // Comprobamos que la Oficina tiene Registro Migrado
                if(!tieneRegistrosMigrados){
                    log.info("Aviso: No hi ha Registres Migrats");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.registroMigrado.list"));
                    response.sendRedirect("/regweb3/aviso");
                    return false;
                }

            }

            // Comprobaciones previas al detalle de un Registro Migrado
            if(url.contains("detalle")){

                if(!rolActivo.getNombre().equals(RegwebConstantes.ROL_USUARI)){
                    log.info("Error de rol");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.rol"));
                    response.sendRedirect("/regweb3/aviso");
                    return false;
                }
            }

            // Comprobaciones previas de un Registro Migrado Lopd
            if(url.contains("lopd")){

                if(!rolActivo.getNombre().equals(RegwebConstantes.ROL_ADMIN)){
                    log.info("Error de rol");
                    Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.rol"));
                    response.sendRedirect("/regweb3/aviso");
                    return false;
                }
            }

            return true;
        } finally {
            //log.info("Interceptor RegistroMigrado: " + TimeUtils.formatElapsedTime(System.currentTimeMillis() - start));
        }
    }


}