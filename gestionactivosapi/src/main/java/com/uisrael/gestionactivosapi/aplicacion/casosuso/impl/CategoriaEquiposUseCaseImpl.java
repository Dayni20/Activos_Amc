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
		if (repositorio.buscarPorNombre(categoriaEquipo.getNombre()).isPresent()) {
			throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoriaEquipo.getNombre());
		}
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

	@Override
	public CategoriaEquipos actualizar(CategoriaEquipos categoriaEquipo) {
		if (repositorio.buscarPorId(categoriaEquipo.getIdCategoria()).isEmpty()) {
			throw new RuntimeException("Categoría no encontrada con ID: " + categoriaEquipo.getIdCategoria());
		}
		
		repositorio.buscarPorNombre(categoriaEquipo.getNombre()).ifPresent(categoriaExistente -> {
			if (categoriaExistente.getIdCategoria() != categoriaEquipo.getIdCategoria()) {
				throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + categoriaEquipo.getNombre());
			}
		});
		
		return repositorio.guardar(categoriaEquipo);
	}

	@Override
	public void eliminar(int id) {
		if (repositorio.buscarPorId(id).isEmpty()) {
			throw new RuntimeException("Categoría no encontrada con ID: " + id);
		}
		repositorio.eliminar(id);
	}

}
