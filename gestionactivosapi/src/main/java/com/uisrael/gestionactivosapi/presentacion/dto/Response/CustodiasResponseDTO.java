package com.uisrael.gestionactivosapi.presentacion.dto.Response;

import java.time.LocalDate;

public class CustodiasResponseDTO {

    private int id_custodia_equipo;
    private int id_equipo;
    private int id_custodio;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String observacion;

    public int getId_custodia_equipo() {
        return id_custodia_equipo;
    }

    public void setId_custodia_equipo(int id_custodia_equipo) {
        this.id_custodia_equipo = id_custodia_equipo;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public int getId_custodio() {
        return id_custodio;
    }

    public void setId_custodio(int id_custodio) {
        this.id_custodio = id_custodio;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
