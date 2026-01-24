package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Custodias implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idCustodiaEquipo;
	private final LocalDate fechaInicio;
	private final LocalDate fechaFin;
	private final String observacion;
	private final boolean estado;

	public Custodias(int idCustodiaEquipo, LocalDate fechaInicio, LocalDate fechaFin, String observacion,
			boolean estado) {
		this.idCustodiaEquipo = idCustodiaEquipo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.observacion = observacion;
		this.estado = estado;
	}

	public int getIdCustodiaEquipo() {
		return idCustodiaEquipo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public String getObservacion() {
		return observacion;
	}
	
	public boolean isEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Custodias [idCustodiaEquipo=" + idCustodiaEquipo + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", observacion=" + observacion + ", estado=" + estado + "]";
	}


}