package es.caib.regweb3.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;



/**
 * Created 18/02/14 14:59
 *
 * @author mgonzalez
 * @author anadal (index)
 */

@Entity
@Table(name = "RWE_ORGANISMO")
@org.hibernate.annotations.Table(appliesTo = "RWE_ORGANISMO", indexes = {
    @Index(name="RWE_ORGANI_ENTIDA_FK_I", columnNames = {"ENTIDAD"}),
    @Index(name="RWE_ORGANI_ESTADO_FK_I", columnNames = {"ESTADO"}),
    @Index(name="RWE_ORGANI_SUPERI_FK_I", columnNames = {"ORGANISMOSUPERIOR"}),
    @Index(name="RWE_ORGANI_RAIZ_FK_I", columnNames = {"ORGANISMORAIZ"}),
    @Index(name="RWE_ORGANI_CAUTON_FK_I", columnNames = {"CODAMBCOMUNIDAD"}),
    @Index(name="RWE_ORGANI_PAIS_FK_I", columnNames = {"PAIS"}),
    @Index(name="RWE_ORGANI_LOCALI_FK_I", columnNames = {"LOCALIDAD"}),
    @Index(name="RWE_ORGANI_TVIA_FK_I", columnNames = {"TIPOVIA"}),
    @Index(name="RWE_ORGANI_PROVIN_FK_I", columnNames = {"CODAMBPROVINCIA"})
})
@SequenceGenerator(name="generator",sequenceName = "RWE_ALL_SEQ", allocationSize = 1)
@XmlRootElement(name = "organismo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organismo implements Serializable {

    @XmlAttribute
    private Long id;
    @XmlElement
    private String codigo;
    @XmlTransient
    private Entidad entidad;
    @XmlTransient
    private List<Libro> libros;
    @XmlTransient
    private Long nivelJerarquico;
    @XmlTransient
    private CatEstadoEntidad estado;
    @XmlTransient
    private Organismo organismoSuperior;
    @XmlTransient
    private Organismo organismoRaiz;
    @XmlElement
    private String denominacion;

    //Externos
    @XmlTransient
    private CatNivelAdministracion nivelAdministracion;
    @XmlTransient
    private CatComunidadAutonoma codAmbComunidad;
    @XmlTransient
    private CatProvincia codAmbProvincia;

    @XmlTransient
    private CatPais codPais;
    @XmlTransient
    private CatLocalidad localidad;
    @XmlTransient
    private CatTipoVia tipoVia;
    @XmlTransient
    private String nombreVia;
    @XmlTransient
    private String numVia;
    @XmlTransient
    private String codPostal;

    @XmlTransient
    private Set<Organismo> historicoUO; // relacion de historicos


    public Organismo() {}

    public Organismo(Long id) {
        this.id = id;
    }
    public Organismo(Long id, String denominacion) {

        this.id = id;
        this.denominacion = denominacion;
    }

    public Organismo(Long id, String codigo, String denominacion) {

        this.id = id;
        this.codigo = codigo;
        this.denominacion = denominacion;
    }

    public Organismo(Long id, String codigo, String denominacion, Long organismoSuperior) {

        this.id = id;
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.organismoSuperior = new Organismo(organismoSuperior);
    }

    public Organismo(Long id, String codigo, String denominacion, List<Libro> libros) {

        this.id = id;
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.libros = libros;
    }

    public Organismo(Organismo organismo) {
        this.id = organismo.getId();
        this.codigo = organismo.getCodigo();
        this.entidad = organismo.getEntidad();
        this.libros = organismo.getLibros();
        this.nivelJerarquico = organismo.getNivelJerarquico();
        this.estado = organismo.getEstado();
        this.organismoSuperior = organismo.getOrganismoSuperior();
        this.organismoRaiz = organismo.getOrganismoRaiz();
        this.denominacion = organismo.getDenominacion();
        this.nivelAdministracion = organismo.getNivelAdministracion();
        this.codAmbComunidad = organismo.getCodAmbComunidad();
        this.codAmbProvincia = organismo.getCodAmbProvincia();
        this.codPais = organismo.getCodPais();
        this.localidad = organismo.getLocalidad();
        this.tipoVia = organismo.getTipoVia();
        this.nombreVia = organismo.getNombreVia();
        this.numVia = organismo.getNumVia();
        this.codPostal = organismo.getCodPostal();
        this.historicoUO = organismo.getHistoricoUO();
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "generator")
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "CODIGO", nullable = false, length = 9)
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Column(name = "DENOMINACION", nullable = false, length = 300)
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ENTIDAD")
    @ForeignKey(name = "RWE_ORGANISMO_ENTIDAD_FK")
    @JsonIgnore
    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ESTADO")
    @ForeignKey(name = "RWE_ORGANISMO_ESTADO_FK")
    @JsonIgnore
    public CatEstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(CatEstadoEntidad estado) {
        this.estado = estado;
    }


    @Column(name = "NIVELJERARQUICO", length = 6)
    public Long getNivelJerarquico() {
        return nivelJerarquico;
    }


    public void setNivelJerarquico(Long nivelJerarquico) {
        this.nivelJerarquico = nivelJerarquico;
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="ORGANISMOSUPERIOR")
    @ForeignKey(name="RWE_ORGANISMO_ORG_SUPERIOR_FK")
    @JsonIgnore
    public Organismo getOrganismoSuperior() {
        return organismoSuperior;
    }

    public void setOrganismoSuperior(Organismo organismoSuperior) {
        this.organismoSuperior = organismoSuperior;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="ORGANISMORAIZ")
    @ForeignKey(name="RWE_ORGANISMO_ORGRAIZ_FK")
    @JsonIgnore
    public Organismo getOrganismoRaiz() {
        return organismoRaiz;
    }

    public void setOrganismoRaiz(Organismo organismoRaiz) {
        this.organismoRaiz = organismoRaiz;
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "organismo")
     /*@LazyCollection(LazyCollectionOption.FALSE)*/
    @OrderColumn(name = "id")
    @OrderBy("id")
    @JsonIgnore
    public List<Libro> getLibros() {
        return libros;
    }



    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="NIVELADMINISTRACION")
    @ForeignKey(name="RWE_ORGANISMO_CATNIVELADMIN_FK")
    @JsonIgnore
    public CatNivelAdministracion getNivelAdministracion() {
        return nivelAdministracion;
    }


    public void setNivelAdministracion(CatNivelAdministracion nivelAdministracion) {
        this.nivelAdministracion = nivelAdministracion;
    }

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="CODAMBCOMUNIDAD")
    @ForeignKey(name="RWE_ORGANISMO_CATAMBCOMAUTO_FK")
    @JsonIgnore
    public CatComunidadAutonoma getCodAmbComunidad() {
        return codAmbComunidad;
    }

    public void setCodAmbComunidad(CatComunidadAutonoma codAmbComunidad) {
        this.codAmbComunidad = codAmbComunidad;
    }

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="CODAMBPROVINCIA")
    @ForeignKey(name="RWE_ORGANISMO_CATPROVINCIA_FK")
    @JsonIgnore
    public CatProvincia getCodAmbProvincia() {
        return codAmbProvincia;
    }

    public void setCodAmbProvincia(CatProvincia codAmbProvincia) {
        this.codAmbProvincia = codAmbProvincia;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PAIS")
    @ForeignKey(name = "RWE_ORGANISMO_PAIS_FK")
    public CatPais getCodPais() {
        return codPais;
    }

    public void setCodPais(CatPais codPais) {
        this.codPais = codPais;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LOCALIDAD")
    @ForeignKey(name = "RWE_ORGANISMO_LOCALIDAD_FK")
    public CatLocalidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(CatLocalidad localidad) {
        this.localidad = localidad;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TIPOVIA")
    @ForeignKey(name = "RWE_ORGANISMO_TIPOVIA_FK")
    public CatTipoVia getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(CatTipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    @Column(name = "NOMBREVIA", length = 300)
    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    @Column(name = "NUMVIA", length = 20)
    public String getNumVia() {
        return numVia;
    }

    public void setNumVia(String numVia) {
        this.numVia = numVia;
    }

    @Column(name = "CODPOSTAL", length = 14)
    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name="RWE_HISTORICOUO",
               joinColumns=@JoinColumn(name="CODANTERIOR"),
               inverseJoinColumns=@JoinColumn(name="CODULTIMA"))
    @ForeignKey(name="RWE_ORG_ORG_HISTANTE_FK", inverseName = "RWE_ORG_ORG_HISTULTI_FK")
    @JsonIgnore
    public Set<Organismo> getHistoricoUO() {
      return historicoUO;
    }


    public void setHistoricoUO(Set<Organismo> historicoUO) {
      this.historicoUO = historicoUO;
    }


    @Transient
    public String getNombreCompleto(){
        return  getCodigo() +" "+ getDenominacion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organismo organismo = (Organismo) o;

        if (id != null ? !id.equals(organismo.id) : organismo.id != null) return false;
        return codigo != null ? codigo.equals(organismo.codigo) : organismo.codigo == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return denominacion;
    }


}
