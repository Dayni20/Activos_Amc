package com.uisrael.gestionactivosapi.presentacion.dto.Response;

public class CustodiosResponseDTO {

    private int idCustodio;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private boolean estado;

    public int getIdCustodio() { return idCustodio; }
    public void setIdCustodio(int idCustodio) { this.idCustodio = idCustodio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
