package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.time.LocalDate;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CargosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;

public class Custodios implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idCustodio;
	private final String nombre;
	private final String cedula;
	private final String correo;
	private final String telefono;
	private final LocalDate fechaIngreso;
	private final boolean estado;

	private DepartamentosJpa fkDepartamento;
	private CargosJpa fkCargo;

	public Custodios(int idCustodio, String nombre, String cedula, String correo, String telefono,
			LocalDate fechaIngreso, boolean estado, DepartamentosJpa fkDepartamento, CargosJpa fkCargo) {
		this.idCustodio = idCustodio;
		this.nombre = nombre;
		this.cedula = cedula;
		this.correo = correo;
		this.telefono = telefono;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
		this.fkDepartamento = fkDepartamento;
		this.fkCargo = fkCargo;
	}

	public int getIdCustodio() {
		return idCustodio;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public String getCorreo() {
		return correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public boolean isEstado() {
		return estado;
	}

	public DepartamentosJpa getFkDepartamento() {
		return fkDepartamento;
	}

	public void setFkDepartamento(DepartamentosJpa fkDepartamento) {
		this.fkDepartamento = fkDepartamento;
	}

	public CargosJpa getFkCargo() {
		return fkCargo;
	}

	public void setFkCargo(CargosJpa fkCargo) {
		this.fkCargo = fkCargo;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	@Override
	public String toString() {
		return "Custodios [idCustodio=" + idCustodio + ", nombre=" + nombre + ", cedula=" + cedula + ", correo="
				+ correo + ", telefono=" + telefono + ", fechaIngreso=" + fechaIngreso + ", estado=" + estado
				+ ", fkDepartamento=" + fkDepartamento + ", fkCargo=" + fkCargo + "]";
	}


}
