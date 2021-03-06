package es.caib.regweb3.webapp.validator;

import es.caib.regweb3.webapp.form.RegistroSalidaBusqueda;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;

/**
 * Created by Fundació BIT.
 * Gestiona las Validaciones del formulario para buscar Registros de Salida
 * @author earrivi
 * Date: 11/02/14
 */
@Component
public class RegistroSalidaBusquedaValidator implements Validator {

    protected final Logger log = Logger.getLogger(getClass());


    @Override
    public boolean supports(Class<?> clazz) {
        return RegistroSalidaBusqueda.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        RegistroSalidaBusqueda busqueda = (RegistroSalidaBusqueda)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaInicio", "error.valor.requerido", "El camp és obligatori");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fechaFin", "error.valor.requerido", "El camp és obligatori");

        if(busqueda.getFechaInicio() != null && busqueda.getFechaFin() != null){

            Calendar fechaInicio = Calendar.getInstance();
            Calendar fechaFin = Calendar.getInstance();

            fechaInicio.setTime(busqueda.getFechaInicio());
            fechaFin.setTime(busqueda.getFechaFin());

            if(fechaFin.before(fechaInicio)){
                errors.rejectValue("fechaFin","error.fecha.anterior","La data de finalització ha de ser major que la d'inici");
            }
        }


    }


}
