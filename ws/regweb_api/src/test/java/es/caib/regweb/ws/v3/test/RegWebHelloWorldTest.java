package es.caib.regweb.ws.v3.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.caib.regweb.ws.api.v3.RegWebHelloWorldWs;

/**
 * 
 * @author anadal
 * 
 */
public class RegWebHelloWorldTest extends RegWebTestUtils {
  
 
  
  protected static RegWebHelloWorldWs helloWorldApi;
  
  /**
   * S'executa una vegada abans de l'execució de tots els tests d'aquesta classe
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    helloWorldApi = getHelloWorldApi();
  }
  
  @Test
  public void testVersio() throws Exception {
    String version = helloWorldApi.getVersion();
    if (version.indexOf('-') != -1) {
      Assert.assertEquals("3.0.0-caib", version);
    } else {
      Assert.assertEquals("3.0.0", version);
    }
  }

  @Test
  public void testVersioWs() throws Exception {
    Assert.assertEquals(3,helloWorldApi.getVersionWs());
  }
  
  
  @Test
  public void testEcho() throws Exception {
    final String echo = "eco ecooooo";
    Assert.assertEquals(helloWorldApi.echo(echo), echo);
  }

  public static void main(String[] args) {
    try {
     
      RegWebHelloWorldWs helloApi = getHelloWorldApi();

      System.out.println("Versió RegWeb   : " + helloApi.getVersion());
      System.out.println("Versió RegWeb-WS: " + helloApi.getVersionWs());


    } catch (Throwable th) {
      th.printStackTrace();
    }
  }


}
