package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class CategoriaEquipos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id_categoria;
	private final String nombre;
	private final boolean estado;
	
	private CategoriaEquipos(int id_categoria, String nombre, boolean estado) {
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.estado = estado;
	}
	
	public int getId_categoria() {
		return id_categoria;
	}
	public String getNombre() {
		return nombre;
	}
	public boolean isEstado() {
		return estado;
	}
	@Override
	public String toString() {
		return "CategoriaEquipos [id_categoria=" + id_categoria + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
	

}
