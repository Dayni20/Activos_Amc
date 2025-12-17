package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Departamentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id_departamento;
	private final String nombre;
	private final int id_ubicacion;
	private final boolean estado;
	
	private Departamentos(int id_departamento, String nombre, int id_ubicacion, boolean estado) {
		this.id_departamento = id_departamento;
		this.nombre = nombre;
		this.id_ubicacion = id_ubicacion;
		this.estado = estado;
	}
	
	public int getId_departamento() {
		return id_departamento;
	}
	public String getNombre() {
		return nombre;
	}
	public int getId_ubicacion() {
		return id_ubicacion;
	}
	public boolean isEstado() {
		return estado;
	}
	
	@Override
	public String toString() {
		return "Departamentos [id_departamento=" + id_departamento + ", nombre=" + nombre + ", id_ubicacion="
				+ id_ubicacion + ", estado=" + estado + "]";
	}
	

}
