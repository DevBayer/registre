
package es.caib.regweb3.ws.api.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listarLibros complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listarLibros">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entidadCodigoDir3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oficinaCodigoDir3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autorizacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarLibros", propOrder = {
    "entidadCodigoDir3",
    "oficinaCodigoDir3",
    "autorizacion"
})
public class ListarLibros {

    protected String entidadCodigoDir3;
    protected String oficinaCodigoDir3;
    protected Long autorizacion;

    /**
     * Gets the value of the entidadCodigoDir3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntidadCodigoDir3() {
        return entidadCodigoDir3;
    }

    /**
     * Sets the value of the entidadCodigoDir3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntidadCodigoDir3(String value) {
        this.entidadCodigoDir3 = value;
    }

    /**
     * Gets the value of the oficinaCodigoDir3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficinaCodigoDir3() {
        return oficinaCodigoDir3;
    }

    /**
     * Sets the value of the oficinaCodigoDir3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficinaCodigoDir3(String value) {
        this.oficinaCodigoDir3 = value;
    }

    /**
     * Gets the value of the autorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAutorizacion() {
        return autorizacion;
    }

    /**
     * Sets the value of the autorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAutorizacion(Long value) {
        this.autorizacion = value;
    }

}
