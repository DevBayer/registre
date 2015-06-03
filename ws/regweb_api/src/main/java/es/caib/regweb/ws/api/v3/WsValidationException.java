
package es.caib.regweb.ws.api.v3;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.4
 * 2015-06-03T10:25:26.916+02:00
 * Generated source version: 2.6.4
 */

@WebFault(name = "WsValidationErrors", targetNamespace = "http://impl.v3.ws.regweb.caib.es/")
public class WsValidationException extends Exception {
    
    private es.caib.regweb.ws.api.v3.WsValidationErrors wsValidationErrors;

    public WsValidationException() {
        super();
    }
    
    public WsValidationException(String message) {
        super(message);
    }
    
    public WsValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WsValidationException(String message, es.caib.regweb.ws.api.v3.WsValidationErrors wsValidationErrors) {
        super(message);
        this.wsValidationErrors = wsValidationErrors;
    }

    public WsValidationException(String message, es.caib.regweb.ws.api.v3.WsValidationErrors wsValidationErrors, Throwable cause) {
        super(message, cause);
        this.wsValidationErrors = wsValidationErrors;
    }

    public es.caib.regweb.ws.api.v3.WsValidationErrors getFaultInfo() {
        return this.wsValidationErrors;
    }
}
