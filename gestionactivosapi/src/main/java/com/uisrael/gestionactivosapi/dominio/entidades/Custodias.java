package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.time.LocalDate;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;

public class Custodias implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int idCustodiaEquipo;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final String observacion;
    private final boolean estado;

    private EquiposJpa fkEquipo;
    private CustodiosJpa fkCustodio;

    public Custodias(int idCustodiaEquipo,
                     LocalDate fechaInicio,
                     LocalDate fechaFin,
                     String observacion,
                     boolean estado,
                     EquiposJpa fkEquipo,
                     CustodiosJpa fkCustodio) {

        this.idCustodiaEquipo = idCustodiaEquipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observacion = observacion;
        this.estado = estado;
        this.fkEquipo = fkEquipo;
        this.fkCustodio = fkCustodio;
    }

    public int getIdCustodiaEquipo() { return idCustodiaEquipo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public String getObservacion() { return observacion; }
    public boolean isEstado() { return estado; }

    public EquiposJpa getFkEquipo() { return fkEquipo; }
    public void setFkEquipo(EquiposJpa fkEquipo) { this.fkEquipo = fkEquipo; }

    public CustodiosJpa getFkCustodio() { return fkCustodio; }
    public void setFkCustodio(CustodiosJpa fkCustodio) { this.fkCustodio = fkCustodio; }

    @Override
    public String toString() {
        return "Custodias [idCustodiaEquipo=" + idCustodiaEquipo
                + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin
                + ", observacion=" + observacion
                + ", estado=" + estado
                + ", fkEquipo=" + fkEquipo
                + ", fkCustodio=" + fkCustodio + "]";
    }
}
