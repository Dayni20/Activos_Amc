package com.uisrael.gestionactivosapi.presentacion.dto.Response;

public class UbicacionesResponseDTO {
	
	private int id_ubicacion;
	private String nombre;
	private String agencia;
	private boolean estado;
	
	public int getId_ubicacion() {
		return id_ubicacion;
	}
	public void setId_ubicacion(int id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	

}
