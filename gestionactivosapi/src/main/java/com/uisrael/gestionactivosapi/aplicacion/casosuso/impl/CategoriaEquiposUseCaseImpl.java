package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICategoriaEquiposUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICategoriaEquiposRepositorio;

public class CategoriaEquiposUseCaseImpl implements ICategoriaEquiposUseCase {
	
	private final ICategoriaEquiposRepositorio repositorio;

	public CategoriaEquiposUseCaseImpl(ICategoriaEquiposRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public CategoriaEquipos crear(CategoriaEquipos categoriaEquipo) {
		return repositorio.guardar(categoriaEquipo);
	}

	@Override
	public CategoriaEquipos obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Categoría de equipo no encontrada"));
	}

	@Override
	public List<CategoriaEquipos> listar() {
		return repositorio.listarTodos();
	}

}
