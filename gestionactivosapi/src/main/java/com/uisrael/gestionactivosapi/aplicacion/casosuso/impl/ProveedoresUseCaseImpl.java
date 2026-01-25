package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IProveedoresUseCase;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.dominio.repositorios.IProveedoresRepositorio;


public class ProveedoresUseCaseImpl implements IProveedoresUseCase{
	
	private final IProveedoresRepositorio repositorio;

	public ProveedoresUseCaseImpl(IProveedoresRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Proveedores crear(Proveedores Proveedores) {
		return repositorio.guardar(Proveedores);
	}

	@Override
	public Proveedores obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Proveedores no encontrado"));
	}

	@Override
	public List<Proveedores> listar() {
		return repositorio.listarTodos();
	}
	
	@Override
	public Proveedores actualizar(int id, Proveedores proveedores) {
		return repositorio.guardar(proveedores);
	}

	@Override
	public void eliminar(int id) {
		repositorio.eliminar(id);
	}

}
