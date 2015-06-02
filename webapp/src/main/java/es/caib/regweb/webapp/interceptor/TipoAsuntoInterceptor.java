package es.caib.regweb.webapp.interceptor;

import es.caib.regweb.model.Descarga;
import es.caib.regweb.model.Entidad;
import es.caib.regweb.model.Rol;
import es.caib.regweb.persistence.ejb.DescargaLocal;
import es.caib.regweb.persistence.ejb.EntidadLocal;
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
 * Interceptor para la gestión de Tipo Asunto
 *
 * @author jpernia
 * Date: 31/12/15
 */
public class TipoAsuntoInterceptor extends HandlerInterceptorAdapter {

    protected final Logger log = Logger.getLogger(getClass());


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String url = request.getServletPath();

            HttpSession session = request.getSession();
            Rol rolActivo = (Rol) session.getAttribute(RegwebConstantes.SESSION_ROL);

            // Cualquier accion con TipoAsunto
            if(!rolActivo.getNombre().equals(RegwebConstantes.ROL_ADMIN)){
                log.info("Error de rol");
                Mensaje.saveMessageAviso(request, I18NUtils.tradueix("aviso.rol"));
                response.sendRedirect("/regweb/aviso");
                return false;
            }

            return true;
        } finally {
            //log.info("Interceptor TipoAsunto: " + TimeUtils.formatElapsedTime(System.currentTimeMillis() - start));
        }

    }

}