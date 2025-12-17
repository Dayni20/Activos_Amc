package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Marcas implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id_marca;
    private final String nombre;
    private final boolean estado;

    private Marcas(int id_marca, String nombre, boolean estado) {
        this.id_marca = id_marca;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId_marca() {
        return id_marca;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Marcas [id_marca=" + id_marca + ", nombre=" + nombre + ", estado=" + estado + "]";
    }
}
