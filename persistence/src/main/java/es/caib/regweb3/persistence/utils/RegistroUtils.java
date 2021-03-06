package es.caib.regweb3.persistence.utils;

import es.caib.regweb3.model.Libro;
import es.caib.regweb3.model.Oficina;
import es.caib.regweb3.model.RegistroEntrada;
import es.caib.regweb3.model.RegistroSalida;
import es.caib.regweb3.model.utils.ReproJson;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fundació BIT.
 * Agrupa todas las funcionalidades comunes para trabajar con Registros de Entrada y Salida
 * @author earrivi
 * Date: 14/07/14
 */

public class RegistroUtils {

    public static final Logger log = Logger.getLogger(RegistroUtils.class);

    private RegistroUtils() {
    }


    /**
     * Serializa en XML un {@link es.caib.regweb3.model.RegistroEntrada}
     * @param bean
     * @return
     */
    public static String serilizarXml(Object bean) throws JAXBException {

        JAXBContext jaxbCtx = null;

        if(bean instanceof RegistroEntrada){
            jaxbCtx = JAXBContext.newInstance(RegistroEntrada.class);
            log.info("Serializa RegistroEntrada");
        }else if(bean instanceof RegistroSalida){
            jaxbCtx = JAXBContext.newInstance(RegistroSalida.class);
            log.info("Serializa RegistroSalida");
        }else if(bean instanceof ReproJson){
            jaxbCtx = JAXBContext.newInstance(ReproJson.class);
            log.info("Serializa ReproJson");
        }

        StringWriter xmlWriter = new StringWriter();
        Marshaller marshaller = jaxbCtx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(bean, xmlWriter);

        return xmlWriter.toString();
    }

    /**
     *  Desserializa un XML en un {@link es.caib.regweb3.model.RegistroEntrada}
     * @param repro
     * @return
     */
    public static ReproJson desSerilizarReproXml(String repro) {

        JAXBContext jaxbCtx = null;
        ReproJson reproJson = null;
        try {
            jaxbCtx = JAXBContext.newInstance(ReproJson.class);
            reproJson = (ReproJson) jaxbCtx.createUnmarshaller().unmarshal(
                    new StringReader(repro));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return reproJson;
    }


    /**
     *  Desserializa un XML en un {@link es.caib.regweb3.model.RegistroEntrada}
     * @param registroEntradaOriginal
     * @return
     */
    public static RegistroEntrada desSerilizarREXml(String registroEntradaOriginal) {

        JAXBContext jaxbCtx = null;
        RegistroEntrada registroEntrada = null;
        try {
            jaxbCtx = JAXBContext.newInstance(RegistroEntrada.class);
            registroEntrada = (RegistroEntrada) jaxbCtx.createUnmarshaller().unmarshal(
                    new StringReader(registroEntradaOriginal));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return registroEntrada;
    }

    /**
     *  Desserializa un XML en un {@link es.caib.regweb3.model.RegistroEntrada}
     * @param registroSalidaOriginal
     * @return
     */
    public static RegistroSalida desSerilizarRSXml(String registroSalidaOriginal) {

        JAXBContext jaxbCtx = null;
        RegistroSalida registroEntrada = null;
        try {
            jaxbCtx = JAXBContext.newInstance(RegistroSalida.class);
            registroEntrada = (RegistroSalida) jaxbCtx.createUnmarshaller().unmarshal(
                    new StringReader(registroSalidaOriginal));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return registroEntrada;
    }

    /**
     * Obtiene los días transcurridos a partir de una fecha comparandolo con la fecha actual
     * @param fechaCreacionRegistro
     * @return
     * @throws Exception
     */
    public static Long obtenerDiasRegistro(Date fechaCreacionRegistro) throws Exception {


       final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
       java.util.Date hoy = new Date(); //Fecha de hoy

       long diferencia = ( hoy.getTime() - fechaCreacionRegistro.getTime() )/MILLSECS_PER_DAY;
       log.info("Dias trascurridos: " + diferencia);
       return diferencia;

    }

    /**
     * Obtiene el Numero de registro Entrada formateado según la configuración de la Entidad a la que pertecene
     * @param registroEntrada
     * @return
     */
    public static  String numeroRegistroFormateado(RegistroEntrada registroEntrada, Libro libro, Oficina oficina) throws Exception{

        String formatNumRegistre = registroEntrada.getUsuario().getEntidad().getNumRegistro();
        if(formatNumRegistre != null){
            SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            formatNumRegistre = formatNumRegistre.replace("${numRegistre}", String.valueOf(registroEntrada.getNumeroRegistro()));
            formatNumRegistre = formatNumRegistre.replace("${codiOficina}", oficina.getCodigo());
            formatNumRegistre = formatNumRegistre.replace("${nomOficina}", oficina.getDenominacion());
            formatNumRegistre = formatNumRegistre.replace("${tipusRegistre}", "E");
            formatNumRegistre = formatNumRegistre.replace("${dataRegistre}", formatDate.format(registroEntrada.getFecha()));
            formatNumRegistre = formatNumRegistre.replace("${anyRegistre}", formatYear.format(registroEntrada.getFecha()));
            formatNumRegistre = formatNumRegistre.replace("${numLlibre}", libro.getCodigo());
            formatNumRegistre = formatNumRegistre.replace("${nomLlibre}", libro.getNombre());
        }

        return  formatNumRegistre;
    }

    /**
     * Obtiene el Numero de registro Salida formateado según la configuración de la Entidad a la que pertecene
     * @param registroSalida
     * @return
     */
    public static String numeroRegistroFormateado(RegistroSalida registroSalida, Libro libro, Oficina oficina) throws Exception{

        String formatNumRegistre = registroSalida.getUsuario().getEntidad().getNumRegistro();
        if(formatNumRegistre != null){
            SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            formatNumRegistre = formatNumRegistre.replace("${numRegistre}", String.valueOf(registroSalida.getNumeroRegistro()));
            formatNumRegistre = formatNumRegistre.replace("${codiOficina}", oficina.getCodigo());
            formatNumRegistre = formatNumRegistre.replace("${nomOficina}", oficina.getDenominacion());
            formatNumRegistre = formatNumRegistre.replace("${tipusRegistre}", "S");
            formatNumRegistre = formatNumRegistre.replace("${dataRegistre}", formatDate.format(registroSalida.getFecha()));
            formatNumRegistre = formatNumRegistre.replace("${anyRegistre}", formatYear.format(registroSalida.getFecha()));
            formatNumRegistre = formatNumRegistre.replace("${numLlibre}", libro.getCodigo());
            formatNumRegistre = formatNumRegistre.replace("${nomLlibre}", libro.getNombre());
        }

        return  formatNumRegistre;
    }

    /**
     * Recibe una fecha y devuelve la misma fecha con hora 23, minutos 59 y segundos 59
     * @param fecha
     * @return
     * @throws Exception
     */
    public static Date ajustarHoraBusqueda(Date fecha) throws Exception{

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(fecha);
        calendarDate.add(Calendar.HOUR, 23);
        calendarDate.add(Calendar.MINUTE, 59);
        calendarDate.add(Calendar.SECOND, 59);
        fecha = calendarDate.getTime();

        return fecha;
    }


}
