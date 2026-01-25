package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.RolesJpa;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idUsuario;
	private final String nombre;
	private final String correo;
	private final String contrasena;
	private final boolean estado;

	private DepartamentosJpa fkDepartamento;
	private RolesJpa fkRol;

	public Usuarios(int idUsuario, String nombre, String correo, String contrasena, boolean estado,
			DepartamentosJpa fkDepartamento, RolesJpa fkRol) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.estado = estado;
		this.fkDepartamento = fkDepartamento;
		this.fkRol = fkRol;
	}

	public DepartamentosJpa getFkDepartamento() {
		return fkDepartamento;
	}

	public void setFkDepartamento(DepartamentosJpa fkDepartamento) {
		this.fkDepartamento = fkDepartamento;
	}

	public RolesJpa getFkRol() {
		return fkRol;
	}

	public void setFkRol(RolesJpa fkRol) {
		this.fkRol = fkRol;
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
			+ contrasena + ", estado=" + estado + ", fkDepartamento=" + fkDepartamento + ", fkRol=" + fkRol + "]";
	}

}
