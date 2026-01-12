package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id_usuario;
	private String nombre;
	private String correo;
	private String contrasena;
	private int id_rol;
	private int id_departamento;
	private String estado;
	
	public Usuarios() {
	}
	
	public Usuarios(int id_usuario, String nombre, String correo, String contrasena, int id_rol, int id_departamento,
			String estado) {
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

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public int getId_departamento() {
		return id_departamento;
	}

	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Usuarios [id_usuario=" + id_usuario + ", nombre=" + nombre + ", correo=" + correo + ", contrasena="
				+ contrasena + ", id_rol=" + id_rol + ", id_departamento=" + id_departamento + ", estado=" + estado
				+ "]";
	}

}
		
