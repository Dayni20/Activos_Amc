package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IMarcasUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.dominio.repositorios.IMarcasRepositorio;


public class MarcasUseCaseImpl implements IMarcasUseCase {
	
	private final IMarcasRepositorio repositorio;

	public MarcasUseCaseImpl(IMarcasRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Marcas crear(Marcas Marcas) {
		return repositorio.guardar(Marcas);
	}

	@Override
	public Marcas obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Marca no encontrado"));
	}

	@Override
	public List<Marcas> listar() {
		return repositorio.listarTodos();
	}

}
