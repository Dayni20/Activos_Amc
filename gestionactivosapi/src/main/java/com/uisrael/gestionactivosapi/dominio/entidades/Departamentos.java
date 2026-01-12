package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Departamentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id_departamento;
	private final String nombre;
	private final boolean estado;
	
	public Departamentos(int id_departamento, String nombre, boolean estado) {
		this.id_departamento = id_departamento;
		this.nombre = nombre;
		this.estado = estado;
	}
	
	public int getId_departamento() {
		return id_departamento;
	}
	public String getNombre() {
		return nombre;
	}

	public boolean isEstado() {
		return estado;
	}
	
	@Override
	public String toString() {
		return "Departamentos [id_departamento=" + id_departamento + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
	

}
