package com.uisrael.gestionactivosapi.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;

public interface IUbicacionesRepositorio {
	
	Ubicaciones guardar(Ubicaciones ubicacion);
	
	Optional<Ubicaciones> buscarPorId(int id);
	
	List<Ubicaciones> listarTodos();
	
}
