package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Custodios implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idCustodio;
	private final String nombre;
	private final String cedula;
	private final String correo;
	private final String telefono;
	private final boolean estado;
	
	public Custodios(int idCustodio, String nombre, String cedula, String correo, String telefono, boolean estado) {
		this.idCustodio = idCustodio;
		this.nombre = nombre;
		this.cedula = cedula;
		this.correo = correo;
		this.telefono = telefono;
		this.estado = estado;
	}

	public int getIdCustodio() {
		return idCustodio;
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

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Custodios [idCustodio=" + idCustodio + ", nombre=" + nombre + ", cedula=" + cedula + ", correo="
				+ correo + ", telefono=" + telefono + ", estado=" + estado + "]";
	}

}
