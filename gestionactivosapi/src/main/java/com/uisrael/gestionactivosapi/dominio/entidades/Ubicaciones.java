package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Ubicaciones implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id_ubicacion;
	private final String nombre;
	private final String agencia;
	private final boolean estado;
	
	public Ubicaciones(int id_ubicacion, String nombre, String agencia, boolean estado) {
		super();
		this.id_ubicacion = id_ubicacion;
		this.nombre = nombre;
		this.agencia = agencia;
		this.estado = estado;
	}
	
	public int getId_ubicacion() {
		return id_ubicacion;
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
		return "Ubicaciones [id_ubicacion=" + id_ubicacion + ", nombre=" + nombre + ", agencia=" + agencia + ", estado="
				+ estado + "]";
	}
	
}
