package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUbicacionesUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUbicacionesRepositorio;

public class UbicacionesUseCaseImpl implements IUbicacionesUseCase{
	
	private final IUbicacionesRepositorio repositorio;
	
	public UbicacionesUseCaseImpl(IUbicacionesRepositorio repositorio) {
		super();
		this.repositorio = repositorio;
	}

	@Override
	public Ubicaciones crear(Ubicaciones ubicacion) {
		return repositorio.guardar(ubicacion);
	}

	@Override
	public Ubicaciones obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
	}

	@Override
	public List<Ubicaciones> listar() {
		return repositorio.listarTodos();
	}
	
	

}
