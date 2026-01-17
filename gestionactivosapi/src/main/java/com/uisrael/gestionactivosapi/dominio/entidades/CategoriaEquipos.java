package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class CategoriaEquipos implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idCategoria;
	private String nombre;

	public CategoriaEquipos(int idCategoria, String nombre) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "CategoriaEquipos [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
	}

}
