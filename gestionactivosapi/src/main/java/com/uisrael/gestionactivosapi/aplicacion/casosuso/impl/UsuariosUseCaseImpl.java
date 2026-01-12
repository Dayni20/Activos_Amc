package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUsuariosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUsuariosRepositorio;

public class UsuariosUseCaseImpl implements IUsuariosUseCase {
	
	private final IUsuariosRepositorio repositorio;

	public UsuariosUseCaseImpl(IUsuariosRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Usuarios crear(Usuarios usuario) {
		return repositorio.guardar(usuario);
	}

	@Override
	public Usuarios obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	@Override
	public List<Usuarios> listar() {
		return repositorio.listarTodos();
	}

	@Override
	public void eliminar(int id) {
		repositorio.eliminar(id);
	}

}
