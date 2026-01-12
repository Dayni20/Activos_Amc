package com.uisrael.gestionactivosapi.presentacion.dto.Response;

public class CustodiosResponseDTO {

    private int id_custodio;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String estado;

    public int getId_custodio() {
        return id_custodio;
    }

    public void setId_custodio(int id_custodio) {
        this.id_custodio = id_custodio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
