package es.caib.regweb.ws.v3.apiaxis.test;

import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;

import es.caib.regweb.ws.v3.apiaxis.RegWebHelloWorldWithSecurityWsServiceLocator;
import es.caib.regweb.ws.v3.apiaxis.RegWebHelloWorldWithSecurityWs_PortType;
import es.caib.regweb.ws.v3.apiaxis.RegWebHelloWorldWsServiceLocator;
import es.caib.regweb.ws.v3.apiaxis.RegWebHelloWorldWs_PortType;
import es.caib.regweb.ws.v3.apiaxis.RegWebRegistroSalidaWsServiceLocator;
import es.caib.regweb.ws.v3.apiaxis.RegWebRegistroSalidaWsServiceSoapBindingStub;
import es.caib.regweb.ws.v3.apiaxis.RegWebRegistroSalidaWs_PortType;





public class TestApiAxis {

  
  public static void main(String[] args) {

    try {
      
      String url = "http://ibit151:8080/regweb/ws/v3/RegWebHelloWorld";

      
      RegWebHelloWorldWsServiceLocator locator = new RegWebHelloWorldWsServiceLocator();
      locator.setRegWebHelloWorldWsEndpointAddress(url);
      RegWebHelloWorldWs_PortType service = locator.getRegWebHelloWorldWs();
      
      
      System.out.println("Versio: " +  service.getVersion());

      /*
 String url = "http://ibit151:8080/regweb/ws/v3/RegWebHelloWorldWithSecurity";

      
 RegWebHelloWorldWithSecurityWsServiceLocator locator = new RegWebHelloWorldWithSecurityWsServiceLocator();
      locator.setRegWebHelloWorldWithSecurityWsEndpointAddress(url);
      RegWebHelloWorldWithSecurityWs_PortType service = locator.getRegWebHelloWorldWithSecurityWs();
      
      ((Stub) service)._setProperty(Call.USERNAME_PROPERTY, "caibapp");
      ((Stub) service)._setProperty(Call.PASSWORD_PROPERTY, "caibapp");
      
      System.out.println("Versio: " +  service.getVersion());
      */
      
/*
    String url = "http://localhost:8080/regweb/ws/v3/RegWebRegistroEntrada";

    
    RegWebRegistroSalidaWsServiceLocator locator = new RegWebRegistroSalidaWsServiceLocator();
    locator.setRegWebRegistroSalidaWsEndpointAddress(url);
    RegWebRegistroSalidaWs_PortType service = locator.getRegWebRegistroSalidaWs();

    // to use Basic HTTP Authentication:
    ((Stub) service)._setProperty(Call.USERNAME_PROPERTY, "user name");
    ((Stub) service)._setProperty(Call.PASSWORD_PROPERTY, "password");
*/

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    
    
  }
  

}