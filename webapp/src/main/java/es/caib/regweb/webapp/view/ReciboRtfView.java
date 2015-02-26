package es.caib.regweb.webapp.view;

import es.caib.regweb.model.ModeloRecibo;
import es.caib.regweb.model.RegistroEntrada;
import es.caib.regweb.model.RegistroSalida;
import es.caib.regweb.model.Usuario;
import es.caib.regweb.persistence.utils.FileSystemManager;
import es.caib.regweb.utils.RegwebConstantes;
import es.caib.regweb.webapp.utils.CombineStream;
import es.caib.regweb.webapp.utils.ConvertirTexto;
import es.caib.regweb.webapp.utils.DatosRecibo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jpernia
 * Date: 25/04/14
 */
public class ReciboRtfView extends AbstractView {

    protected final Logger log = Logger.getLogger(getClass());

    public ReciboRtfView() {
        setContentType("text/rtf;charset=UTF-8");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {


        Object object = model.get("registro");
        ModeloRecibo modeloRecibo = (ModeloRecibo) model.get("modeloRecibo");

        DatosRecibo datosRecibo = null;
        String formatNumRegistre = null;
        String interessats = "";

        // Registre Entrada
        if(object.getClass().getSimpleName().equals("RegistroEntrada")){

            RegistroEntrada registro = (RegistroEntrada) model.get("registro");
            datosRecibo = new DatosRecibo(registro,"Entrada");
            formatNumRegistre = registro.getNumeroRegistroFormateado();
        }

        // Registre Sortida
        if(object.getClass().getSimpleName().equals("RegistroSalida")){

            RegistroSalida registro = (RegistroSalida) model.get("registro");
            datosRecibo = new DatosRecibo(registro,"Salida");
            formatNumRegistre = registro.getNumeroRegistroFormateado();
        }

        String idiomaActual = request.getLocale().getLanguage();
        File archivo = FileSystemManager.getArchivo(modeloRecibo.getModelo().getId());

        // Convertimos el archivo a array de bytes
        byte[] datos = FileUtils.readFileToByteArray(archivo);
        InputStream is = new ByteArrayInputStream(datos);
        java.util.Hashtable<String,Object> ht = new java.util.Hashtable<String,Object>();

        // Extraemos año de la fecha del registro
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        String anoRegistro = formatYear.format(datosRecibo.getFechaRegistro());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        String fechaRegistro = formatDate.format(datosRecibo.getFechaRegistro());
        String fechaRecibo = "";

        // Fecha según el idioma y mes
        Date dataActual = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMMMM", new Locale("es"));
        SimpleDateFormat sdf4 = new SimpleDateFormat("MMMMM", new Locale("ca"));
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
        String diaRecibo = sdf1.format(dataActual);
        String mesRecibo = "";
        String anoRecibo = sdf3.format(dataActual);

        if(idiomaActual.equals("es")){
            mesRecibo = sdf2.format(dataActual);
            fechaRecibo = diaRecibo + " de " + mesRecibo + " de " + anoRecibo;
        }
        if(idiomaActual.equals("ca")){
            mesRecibo = sdf4.format(dataActual);
            if(mesRecibo.startsWith("a") || mesRecibo.startsWith("o")){
                mesRecibo= " d'" + mesRecibo;
            }else{
                mesRecibo= " de " + mesRecibo;
            }
            fechaRecibo = diaRecibo + mesRecibo + " de " + anoRecibo;
        }

        // Interessats
        if (datosRecibo.getInteresados()!=null){
            // TODO Optimitzar foreach i switch
            for(int i=0;i<datosRecibo.getInteresados().size();i++){
                if(datosRecibo.getInteresados().get(i).getTipo() == RegwebConstantes.TIPO_INTERESADO_ADMINISTRACION){
                    interessats = interessats + datosRecibo.getInteresados().get(i).getNombre();
                }
                if(datosRecibo.getInteresados().get(i).getTipo() == RegwebConstantes.TIPO_INTERESADO_PERSONA_FISICA){
                    interessats = interessats + datosRecibo.getInteresados().get(i).getNombrePersonaFisica();
                }
                if(datosRecibo.getInteresados().get(i).getTipo() == RegwebConstantes.TIPO_INTERESADO_PERSONA_JURIDICA){
                    interessats = interessats + datosRecibo.getInteresados().get(i).getNombrePersonaJuridica();
                }
                if(i<datosRecibo.getInteresados().size()-1){
                    interessats = interessats + ", ";
                }
            }
        }

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute(RegwebConstantes.SESSION_USUARIO);

        // Mapeamos los campos del rtf con los del registro
        if (datosRecibo.getCodigoOficina()!=null) ht.put("(codiOficina)", ConvertirTexto.toCp1252(datosRecibo.getCodigoOficina()));
        if (datosRecibo.getNombreOficina()!=null) ht.put("(nomOficina)", ConvertirTexto.toCp1252(datosRecibo.getNombreOficina()));
        if (datosRecibo.getNumeroRegistro()!=null) ht.put("(numRegistre)", ConvertirTexto.toCp1252(datosRecibo.getNumeroRegistro()));
        if (anoRegistro!=null)ht.put("(anyRegistre)", ConvertirTexto.toCp1252(anoRegistro));
        if (fechaRegistro!=null) ht.put("(dataRegistre)", ConvertirTexto.toCp1252(fechaRegistro));
        if (datosRecibo.getDestinatario()!=null) ht.put("(destinatari)", ConvertirTexto.toCp1252(datosRecibo.getDestinatario()));
        if (datosRecibo.getTipoRegistro()!=null) ht.put("(tipusRegistre)", ConvertirTexto.toCp1252(datosRecibo.getTipoRegistro()));
        if (datosRecibo.getExtracto()!=null) ht.put("(extracte)", ConvertirTexto.toCp1252(datosRecibo.getExtracto()));
        if (datosRecibo.getLibro()!=null) ht.put("(llibre)", ConvertirTexto.toCp1252(datosRecibo.getLibro()));
        if (datosRecibo.getUsuarioNombre()!=null) ht.put("(nomUsuariRegistre)", ConvertirTexto.toCp1252(datosRecibo.getUsuarioNombre()));
        if (datosRecibo.getUsuarioNombreCompleto()!=null) ht.put("(nomUsuariCompletRegistre)", ConvertirTexto.toCp1252(datosRecibo.getUsuarioNombreCompleto()));
        if (datosRecibo.getEntitat()!=null) ht.put("(entitat)", ConvertirTexto.toCp1252(datosRecibo.getEntitat()));
        if (datosRecibo.getDecodificacioEntitat()!=null) ht.put("(decodificacioEntitat)", ConvertirTexto.toCp1252(datosRecibo.getDecodificacioEntitat()));
        if (interessats!=null) ht.put("(interessats)", ConvertirTexto.toCp1252(interessats));
        if (usuario!=null) ht.put("(nomUsuari)", ConvertirTexto.toCp1252(usuario.getNombreCompleto()));
        if (fechaRecibo!=null) ht.put("(dataRebut)", ConvertirTexto.toCp1252(fechaRecibo));
        ht.put("(formatNumRegistre)", ConvertirTexto.toCp1252(formatNumRegistre));

        // Reemplaza el texto completo
        ht.put("(read_only)", ConvertirTexto.getISOBytes("\\annotprot\\readprot\\enforceprot1\\protlevel3\\readonlyrecommended "));

        CombineStream cs = new CombineStream(is, ht);
        
        try {

        // Retornamos el archivo
        String nombreFichero = "Rebut_"+ formatNumRegistre +".rtf";

        response.setHeader("Content-Type", "text/rtf;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+nombreFichero);

        response.setContentType(getContentType());

        // Flush byte array to servlet output stream.
        ServletOutputStream outstream = response.getOutputStream();
        int l=0;
        while ((l=cs.read())!=-1) {
            outstream.write(l);
        }
        outstream.close();
        outstream.flush();
        
        } finally {
          try { cs.close(); } catch (Exception e) {};
        }

    }
}