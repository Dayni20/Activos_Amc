package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Custodios implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id_custodio;
    private final String nombre;
    private final String cedula;
    private final String correo;
    private final String telefono;
    private final String estado;

    private Custodios(
            int id_custodio,
            String nombre,
            String cedula,
            String correo,
            String telefono,
            String estado
    ) {
        this.id_custodio = id_custodio;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getId_custodio() {
        return id_custodio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Custodios [id_custodio=" + id_custodio +
                ", nombre=" + nombre +
                ", cedula=" + cedula +
                ", correo=" + correo +
                ", telefono=" + telefono +
                ", estado=" + estado + "]";
    }
}
