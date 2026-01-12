package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;

public interface IUbicacionesUseCase {
	
	Ubicaciones crear (Ubicaciones ubicacion);
	
	Ubicaciones obtenerPorId (int id);
	
	List<Ubicaciones> listar();

}
