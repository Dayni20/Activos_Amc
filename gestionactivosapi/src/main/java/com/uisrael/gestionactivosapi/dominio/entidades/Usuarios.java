package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idUsuario;
	private String nombre;
	private String correo;
	private String contrasena;
	private String estado;

	private DepartamentosJpa fkDepartamento;

	public Usuarios(int idUsuario, String nombre, String correo, String contrasena, String estado,
			DepartamentosJpa fkDepartamento) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.estado = estado;
		this.fkDepartamento = fkDepartamento;
	}

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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public DepartamentosJpa getFkDepartamento() {
		return fkDepartamento;
	}

	public void setFkDepartamento(DepartamentosJpa fkDepartamento) {
		this.fkDepartamento = fkDepartamento;
	}

	@Override
	public String toString() {
		return "Usuarios [idUsuario=" + idUsuario + ", nombre=" + nombre + ", correo=" + correo + ", contrasena="
				+ contrasena + ", estado=" + estado + ", fkDepartamento=" + fkDepartamento + "]";
	}

}
