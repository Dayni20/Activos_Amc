package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Ubicaciones implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idUbicacion;
	private final String nombre;
	private final String agencia;
	private final boolean estado;

	public Ubicaciones(int idUbicacion, String nombre, String agencia, boolean estado) {
		this.idUbicacion = idUbicacion;
		this.nombre = nombre;
		this.agencia = agencia;
		this.estado = estado;
	}

	public int getIdUbicacion() {
		return idUbicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getAgencia() {
		return agencia;
	}

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Ubicaciones [idUbicacion=" + idUbicacion + ", nombre=" + nombre + ", agencia=" + agencia + ", estado="
				+ estado + "]";
	}

}
