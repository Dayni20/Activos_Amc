package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Custodias implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int idCustodiaEquipo;
	private final int idEquipo;
	private final int idCustodio;
	private final LocalDate fechaInicio;
	private final LocalDate fechaFin;
	private final String observacion;

	public Custodias(int idCustodiaEquipo, int idEquipo, int idCustodio, LocalDate fechaInicio, LocalDate fechaFin,
			String observacion) {
		this.idCustodiaEquipo = idCustodiaEquipo;
		this.idEquipo = idEquipo;
		this.idCustodio = idCustodio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.observacion = observacion;
	}

	public int getIdCustodiaEquipo() {
		return idCustodiaEquipo;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public int getIdCustodio() {
		return idCustodio;
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

	@Override
	public String toString() {
		return "Custodias [idCustodiaEquipo=" + idCustodiaEquipo + ", idEquipo=" + idEquipo + ", idCustodio="
				+ idCustodio + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", observacion="
				+ observacion + "]";
	}

}