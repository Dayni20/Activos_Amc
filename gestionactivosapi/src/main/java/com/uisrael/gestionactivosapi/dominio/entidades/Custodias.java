package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Custodias implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id_custodia_equipo;

    private final int id_equipo;
    private final int id_custodio;

    private final LocalDate fecha_inicio;
    private final LocalDate fecha_fin;

    private final String observacion;

    public Custodias(
            int id_custodia_equipo,
            int id_equipo,
            int id_custodio,
            LocalDate fecha_inicio,
            LocalDate fecha_fin,
            String observacion
    ) {
        this.id_custodia_equipo = id_custodia_equipo;
        this.id_equipo = id_equipo;
        this.id_custodio = id_custodio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.observacion = observacion;
    }

    public int getId_custodia_equipo() {
        return id_custodia_equipo;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public int getId_custodio() {
        return id_custodio;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public String getObservacion() {
        return observacion;
    }

    @Override
    public String toString() {
        return "Custodias [" +
                "id_custodia_equipo=" + id_custodia_equipo +
                ", id_equipo=" + id_equipo +
                ", id_custodio=" + id_custodio +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", observacion=" + observacion +
                "]";
    }
}