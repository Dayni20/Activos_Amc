package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.UbicacionesJpa;

public class Departamentos implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idDepartamento;
	private final String nombre;
	private final boolean estado;

	private UbicacionesJpa fkUbicacion;

	public Departamentos(int idDepartamento, String nombre, boolean estado, UbicacionesJpa fkUbicacion) {
		this.idDepartamento = idDepartamento;
		this.nombre = nombre;
		this.estado = estado;
		this.fkUbicacion = fkUbicacion;
	}

	public UbicacionesJpa getFkUbicacion() {
		return fkUbicacion;
	}

	public void setFkUbicacion(UbicacionesJpa fkUbicacion) {
		this.fkUbicacion = fkUbicacion;
	}

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Departamentos [idDepartamento=" + idDepartamento + ", nombre=" + nombre + ", estado=" + estado
				+ ", fkUbicacion=" + fkUbicacion + "]";
	}

}
