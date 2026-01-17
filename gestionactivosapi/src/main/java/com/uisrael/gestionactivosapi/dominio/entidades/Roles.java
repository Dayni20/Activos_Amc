package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idRol;
	private String nombre;

	public Roles(int idRol, String nombre) {
		this.idRol = idRol;
		this.nombre = nombre;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Roles [idRol=" + idRol + ", nombre=" + nombre + "]";
	}

}
