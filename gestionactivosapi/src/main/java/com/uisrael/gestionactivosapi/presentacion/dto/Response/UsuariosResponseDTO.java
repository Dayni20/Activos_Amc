package com.uisrael.gestionactivosapi.presentacion.dto.Response;

public class UsuariosResponseDTO {

	private int idUsuario;
	private String nombre;
	private String correo;
	private String estado;

	private DepartamentosResponseDTO fkDepartamento;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public DepartamentosResponseDTO getFkDepartamento() {
		return fkDepartamento;
	}

	public void setFkDepartamento(DepartamentosResponseDTO fkDepartamento) {
		this.fkDepartamento = fkDepartamento;
	}

}
