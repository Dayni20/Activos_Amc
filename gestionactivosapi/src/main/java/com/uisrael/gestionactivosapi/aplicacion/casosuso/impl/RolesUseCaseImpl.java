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
		// Validación case-insensitive: convierte a minúsculas para comparar
		String nombreNormalizado = rol.getNombre().trim().toLowerCase();
		
		// Busca si existe algún rol con el mismo nombre (ignorando mayúsculas/minúsculas)
		List<Roles> todosLosRoles = repositorio.listarTodos();
		boolean existe = todosLosRoles.stream()
			.anyMatch(r -> r.getNombre().trim().toLowerCase().equals(nombreNormalizado));
		
		if (existe) {
			throw new IllegalArgumentException("Ya existe un rol con el nombre '" + rol.getNombre() + "' (sin importar mayúsculas/minúsculas)");
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
		
		// Validación case-insensitive para actualización
		String nombreNormalizado = rol.getNombre().trim().toLowerCase();
		List<Roles> todosLosRoles = repositorio.listarTodos();
		
		boolean existeOtro = todosLosRoles.stream()
			.anyMatch(r -> r.getIdRol() != rol.getIdRol() && 
			             r.getNombre().trim().toLowerCase().equals(nombreNormalizado));
		
		if (existeOtro) {
			throw new IllegalArgumentException("Ya existe otro rol con el nombre '" + rol.getNombre() + "' (sin importar mayúsculas/minúsculas)");
		}
		
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
