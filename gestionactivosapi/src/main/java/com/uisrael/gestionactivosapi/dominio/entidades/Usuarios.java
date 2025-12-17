package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int id_usuario;
	private final String nombre;
	private final String correo;
	private final String contrasena;
	private final int id_rol;
	private final int id_departamento;
	private final boolean estado;
	
	private Usuarios(int id_usuario, String nombre, String correo, String contrasena, int id_rol, int id_departamento,
			boolean estado) {
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.id_rol = id_rol;
		this.id_departamento = id_departamento;
		this.estado = estado;
	}

	public int getId_usuario() {
		return id_usuario;
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

	public int getId_rol() {
		return id_rol;
	}

	public int getId_departamento() {
		return id_departamento;
	}

	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Usuarios [id_usuario=" + id_usuario + ", nombre=" + nombre + ", correo=" + correo + ", contrasena="
				+ contrasena + ", id_rol=" + id_rol + ", id_departamento=" + id_departamento + ", estado=" + estado
				+ "]";
	}
	
}
