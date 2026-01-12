package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Roles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id_rol;
	private String nombre;
	
	public Roles() {
	}
	
	public Roles(int id_rol, String nombre) {
		this.id_rol = id_rol;
		this.nombre = nombre;
	}
	
	public int getId_rol() {
		return id_rol;
	}
	
	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Roles [id_rol=" + id_rol + ", nombre=" + nombre + "]";
	}

}
