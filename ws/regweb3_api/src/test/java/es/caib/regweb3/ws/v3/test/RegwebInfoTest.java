package es.caib.regweb3.ws.v3.test;

import es.caib.regweb3.ws.api.v3.*;
import es.caib.regweb3.ws.api.v3.utils.WsClientUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created 11/12/14 9:01
 *
 * @author mgonzalez
 */
public class RegwebInfoTest extends RegWebTestUtils {

  protected static RegWebInfoWs infoApi;

   /**
   * S'executa una vegada abans de l'execució de tots els tests d'aquesta classe
   *
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    infoApi = getInfoApi();
  }

  //@Test
  public void testVersio() throws Exception {
    String version = infoApi.getVersion();
    if (version.indexOf('-') != -1) {
      Assert.assertEquals("3.0.0-caib", version);
    } else {
      Assert.assertEquals("3.0.0", version);
    }
  }

  //@Test
  public void testVersioWs() throws Exception {
    Assert.assertEquals(3, infoApi.getVersionWs());
  }

  @Test
  public void testTiposAsuntos(){
    String usuario = "earrivi";
    String codigoEntidadDir3 = "A04006741";

    try {
      List<TipoAsuntoWs> tas= infoApi.listarTipoAsunto(codigoEntidadDir3);

      System.out.println("tas num: " + tas.size());

    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      Assert.fail("Exception no esperada");
    }

  }

  @Test
  public void testCodigosAsuntos(){
    String usuario = "earrivi";
    String codigoEntidadDir3 = "A04006741";

    try {
      List<TipoAsuntoWs> tas= infoApi.listarTipoAsunto(codigoEntidadDir3);


      TipoAsuntoWs tasws = tas.get(0);

      List<CodigoAsuntoWs> cas = infoApi.listarCodigoAsunto(codigoEntidadDir3, tasws.getCodigo());
      System.out.println("cas num: " + cas.size());
      System.out.println("CA nombre "+ cas.get(0).getNombre());

    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      Assert.fail("WsValidationException no esperada");
    }

  }

  //@Test
  public void testObtenerLibrosOficina(){
    String codigoEntidadDir3 = "A04006741";
    try {
      List<LibroOficinaWs> librosOficinas = infoApi.obtenerLibrosOficina(codigoEntidadDir3, (long) 1);

      for (LibroOficinaWs libroOficinaWs : librosOficinas) {

        System.out.println("Libro: " + libroOficinaWs.getLibroWs().getNombreLargo());
        System.out.println("Oficina: " + libroOficinaWs.getOficinaWs().getNombre());
        System.out.println("");

      }


    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      //Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      //Assert.fail("WsValidationException no esperada");
    }

  }

  @Test
  public void testObtenerTiposDocumental(){
    String codigoEntidadDir3 = "A04006741";

    try {
      List<TipoDocumentalWs> tipos = infoApi.listarTipoDocumental(codigoEntidadDir3);

      for(TipoDocumentalWs tipo:tipos){
        System.out.println("TipoDocumental: " + tipo.getCodigoNTI());
      }

    } catch (WsI18NException e) {
      e.printStackTrace();
    }


  }

  @Test
  public void testObtenerLibrosOficinaUsuario(){
    String codigoEntidadDir3 = "A04006741";
    String usuario = "earrivi";
    try {
      List<LibroOficinaWs> librosOficinas = infoApi.obtenerLibrosOficinaUsuario(codigoEntidadDir3, usuario, (long) 1);

      for (LibroOficinaWs libroOficinaWs : librosOficinas) {

        System.out.println("Libro: " + libroOficinaWs.getLibroWs().getNombreLargo());
        System.out.println("Oficina: " + libroOficinaWs.getOficinaWs().getNombre());
        System.out.println("");

      }


    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      //Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      //Assert.fail("WsValidationException no esperada");
    }

  }

  //@Test
  public void testOficinas(){
    String codigoEntidadDir3 = "A04006741";
    try {
      List<OficinaWs> oficinas = infoApi.listarOficinas(codigoEntidadDir3, (long) 1);

      for (OficinaWs oficina : oficinas) {
        System.out.println("Oficina: " + oficina.getNombre());
      }


    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      //Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      //Assert.fail("WsValidationException no esperada");
    }

  }

  //@Test
  public void testLibros(){

    String codigoEntidadDir3 = "A04006741";
    String codigoOficinaDir3 = "O0001597";

    try {
      List<LibroWs> libros = infoApi.listarLibros(codigoEntidadDir3, codigoOficinaDir3,(long) 4);

      for (LibroWs libro : libros) {
        System.out.println("Libro: " + libro.getNombreLargo());
      }


    } catch (WsI18NException i18ne) {
      System.err.println(WsClientUtils.toString(i18ne));
      //Assert.assertEquals("error.valor.requerido.ws", i18ne.getFaultInfo().getTranslation().getCode());

    } catch (Exception e) {
      System.err.println("Error desconegut: " + e.getMessage());
      //Assert.fail("WsValidationException no esperada");
    }

  }
}
