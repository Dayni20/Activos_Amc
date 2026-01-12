package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;

public interface ICategoriaEquiposUseCase {
	
	CategoriaEquipos crear(CategoriaEquipos categoriaEquipo);
	
	CategoriaEquipos obtenerPorId(int id);
	
	List<CategoriaEquipos> listar();

}
