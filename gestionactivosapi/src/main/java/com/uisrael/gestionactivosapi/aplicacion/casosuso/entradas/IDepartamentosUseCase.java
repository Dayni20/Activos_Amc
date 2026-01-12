package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Departamentos;

public interface IDepartamentosUseCase {
	
	Departamentos crear (Departamentos departamento);
	
	Departamentos obtenerPorId(int id);
	
	List<Departamentos> listar();

}
