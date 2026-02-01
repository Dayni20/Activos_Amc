package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IRolesUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Roles;
import com.uisrael.gestionactivosapi.dominio.repositorios.IRolesRepositorio;

public class RolesUseCaseImpl implements IRolesUseCase {
	
	private final IRolesRepositorio repositorio;

	public RolesUseCaseImpl(IRolesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Roles crear(Roles rol) {
		if (repositorio.buscarPorNombre(rol.getNombre()).isPresent()) {
			throw new IllegalArgumentException("Ya existe un rol con el nombre: " + rol.getNombre());
		}
		return repositorio.guardar(rol);
	}

	@Override
	public Roles obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
	}

	@Override
	public List<Roles> listar() {
		return repositorio.listarTodos();
	}

	@Override
	public Roles actualizar(Roles rol) {
		if (repositorio.buscarPorId(rol.getIdRol()).isEmpty()) {
			throw new RuntimeException("Rol no encontrado con ID: " + rol.getIdRol());
		}
		
		repositorio.buscarPorNombre(rol.getNombre()).ifPresent(rolExistente -> {
			if (rolExistente.getIdRol() != rol.getIdRol()) {
				throw new IllegalArgumentException("Ya existe otro rol con el nombre: " + rol.getNombre());
			}
		});
		
		return repositorio.guardar(rol);
	}

	@Override
	public void eliminar(int id) {
		if (repositorio.buscarPorId(id).isEmpty()) {
			throw new RuntimeException("Rol no encontrado con ID: " + id);
		}
		repositorio.eliminar(id);
	}

}
