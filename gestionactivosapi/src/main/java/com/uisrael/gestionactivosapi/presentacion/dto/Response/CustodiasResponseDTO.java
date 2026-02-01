package com.uisrael.gestionactivosapi.presentacion.dto.Response;

import java.time.LocalDate;

public class CustodiasResponseDTO {

    private int idCustodiaEquipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observacion;
    private boolean estado;

    private int idEquipo;
    private int idCustodio;

    public int getIdCustodiaEquipo() { return idCustodiaEquipo; }
    public void setIdCustodiaEquipo(int idCustodiaEquipo) { this.idCustodiaEquipo = idCustodiaEquipo; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public int getIdCustodio() { return idCustodio; }
    public void setIdCustodio(int idCustodio) { this.idCustodio = idCustodio; }
}
