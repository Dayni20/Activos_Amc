package com.uisrael.gestionactivosapi.presentacion.dto.Response;

import java.time.LocalDate;

public class CustodiasResponseDTO {

	private int idCustodia_equipo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String observacion;

	public int getIdCustodia_equipo() {
		return idCustodia_equipo;
	}

	public void setIdCustodia_equipo(int idCustodia_equipo) {
		this.idCustodia_equipo = idCustodia_equipo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
