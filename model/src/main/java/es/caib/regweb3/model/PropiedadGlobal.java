package es.caib.regweb3.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by Fundació Bit on 05/05/16
 *
 * @author earrivi
 * @author anadal
 */
@Entity
@Table(name = "RWE_PROPIEDADGLOBAL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CLAVE", "ENTIDAD"})})
@SequenceGenerator(name = "generator", sequenceName = "RWE_ALL_SEQ", allocationSize = 1)
public class PropiedadGlobal implements Serializable {

    private Long id;
    private String clave;
    private String valor;
    private String descripcion;
    private Long entidad;

    public PropiedadGlobal() {

    }

    public PropiedadGlobal(Long idEntidad) {
        this.entidad = idEntidad;
    }

    public PropiedadGlobal(String clave, String valor, String descripcion, Long entidad) {
        this.clave = clave;
        this.valor = valor;
        this.descripcion = descripcion;
        this.entidad = entidad;
    }

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CLAVE", nullable = false, length = 255)
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Column(name = "VALOR", length = 255)
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Column(name = "DESCRIPCION", length = 255)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Index(name = "RWE_PROPIE_ENTIDA_FK_I")
    @Column(name = "ENTIDAD", length = 50)
    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }

    @Transient
    private Integer pageNumber;

    @Transient
    public Integer getPageNumber() {
        return pageNumber;
    }

    @Transient
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropiedadGlobal that = (PropiedadGlobal) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}