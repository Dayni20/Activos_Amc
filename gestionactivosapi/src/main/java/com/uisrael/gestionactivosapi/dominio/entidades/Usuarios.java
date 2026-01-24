package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idUsuario;
	private final String nombre;
	private final String correo;
	private final String contrasena;
	private final boolean estado;

	private DepartamentosJpa fkDepartamento;

	public Usuarios(int idUsuario, String nombre, String correo, String contrasena, boolean estado,
			DepartamentosJpa fkDepartamento) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.estado = estado;
		this.fkDepartamento = fkDepartamento;
	}

	public DepartamentosJpa getFkDepartamento() {
		return fkDepartamento;
	}

	public void setFkDepartamento(DepartamentosJpa fkDepartamento) {
		this.fkDepartamento = fkDepartamento;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Usuarios [idUsuario=" + idUsuario + ", nombre=" + nombre + ", correo=" + correo + ", contrasena="
				+ contrasena + ", estado=" + estado + ", fkDepartamento=" + fkDepartamento + "]";
	}

}
