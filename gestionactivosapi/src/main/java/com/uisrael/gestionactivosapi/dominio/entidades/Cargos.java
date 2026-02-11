package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Cargos implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idCargo;
	private final String nombre;
	private final boolean estado;

	public Cargos(int idCargo, String nombre, boolean estado) {
		this.idCargo = idCargo;
		this.nombre = nombre;
		this.estado = estado;
	}

	public int getIdCargo() {
		return idCargo;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Cargos [idCargo=" + idCargo + ", nombre=" + nombre + ", estado=" + estado + "]";
	}


}
