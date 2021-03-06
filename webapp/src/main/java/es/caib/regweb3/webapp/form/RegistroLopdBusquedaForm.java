package es.caib.regweb3.webapp.form;

import java.util.Date;

/**
 * Created 03/10/14 14:37
 * @author jpernia
 */
public class RegistroLopdBusquedaForm {

    private Date fechaInicio;
    private Date fechaFin;
    private Long libro;
    private Integer numeroRegistro;
    private Long tipoRegistro;

    public RegistroLopdBusquedaForm(Date fechaInicio, Date fechaFin, Long libro, Integer numeroRegistro, Long tipoRegistro) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.libro = libro;
        this.numeroRegistro = numeroRegistro;
        this.tipoRegistro = tipoRegistro;
    }

    public RegistroLopdBusquedaForm() {

    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getLibro() {
        return libro;
    }

    public void setLibro(Long libro) {
        this.libro = libro;
    }

    public Integer getNumeroRegistro() { return numeroRegistro; }

    public void setNumeroRegistro(Integer numeroRegistro) { this.numeroRegistro = numeroRegistro; }

    public Long getTipoRegistro() { return tipoRegistro; }

    public void setTipoRegistro(Long tipoRegistro) { this.tipoRegistro = tipoRegistro; }
}