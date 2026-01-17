package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;

public class Proveedores implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idProveedor;
	private final String nombre;
	private final String ruc;
	private final String telefono;
	private final String correo;
	private final String direccion;
	private final String estado;

	public Proveedores(int idProveedor, String nombre, String ruc, String telefono, String correo, String direccion,
			String estado) {
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.ruc = ruc;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.estado = estado;
	}

	public static Proveedores of(int idProveedor, String nombre, String ruc, String telefono, String correo,
			String direccion, String estado) {
		return new Proveedores(idProveedor, nombre, ruc, telefono, correo, direccion, estado);
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public String getRuc() {
		return ruc;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Proveedores [idProveedor=" + idProveedor + ", nombre=" + nombre + ", ruc=" + ruc + ", telefono="
				+ telefono + ", correo=" + correo + ", direccion=" + direccion + ", estado=" + estado + "]";
	}

}
