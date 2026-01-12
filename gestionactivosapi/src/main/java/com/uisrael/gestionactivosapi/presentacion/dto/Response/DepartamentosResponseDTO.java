package com.uisrael.gestionactivosapi.presentacion.dto.Response;

public class DepartamentosResponseDTO {
	
	private int id_departamento;
	private String nombre;
	private boolean estado;
	
	public int getId_departamento() {
		return id_departamento;
	}
	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	
}
