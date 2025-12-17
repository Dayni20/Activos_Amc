package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id_rol;
	private final String nombre;
	private final boolean estado;
	
	private Roles(int id_rol, String nombre, boolean estado) {
		this.id_rol = id_rol;
		this.nombre = nombre;
		this.estado = estado;
	}
	
	public int getId_rol() {
		return id_rol;
	}
	public String getNombre() {
		return nombre;
	}
	public boolean isEstado() {
		return estado;
	}
	
	@Override
	public String toString() {
		return "Roles [id_rol=" + id_rol + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
	

}
