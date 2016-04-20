package es.caib.regweb3.ws.api.v3;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2016-04-20T09:43:19.719+02:00
 * Generated source version: 2.6.4
 * 
 */
@WebServiceClient(name = "RegWebPersonasWsService", 
                  wsdlLocation = "http://localhost:8080/regweb3/ws/v3/RegWebPersonas?wsdl",
                  targetNamespace = "http://impl.v3.ws.regweb3.caib.es/") 
public class RegWebPersonasWsService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.v3.ws.regweb3.caib.es/", "RegWebPersonasWsService");
    public final static QName RegWebPersonasWs = new QName("http://impl.v3.ws.regweb3.caib.es/", "RegWebPersonasWs");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/regweb3/ws/v3/RegWebPersonas?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RegWebPersonasWsService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/regweb3/ws/v3/RegWebPersonas?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RegWebPersonasWsService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RegWebPersonasWsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RegWebPersonasWsService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns RegWebPersonasWs
     */
    @WebEndpoint(name = "RegWebPersonasWs")
    public RegWebPersonasWs getRegWebPersonasWs() {
        return super.getPort(RegWebPersonasWs, RegWebPersonasWs.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RegWebPersonasWs
     */
    @WebEndpoint(name = "RegWebPersonasWs")
    public RegWebPersonasWs getRegWebPersonasWs(WebServiceFeature... features) {
        return super.getPort(RegWebPersonasWs, RegWebPersonasWs.class, features);
    }

}
