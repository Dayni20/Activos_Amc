package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUsuariosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Usuarios;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUsuariosRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IRolesRepositorio;
import com.uisrael.gestionactivosapi.dominio.repositorios.IDepartamentosRepositorio;

public class UsuariosUseCaseImpl implements IUsuariosUseCase {
    
	private final IUsuariosRepositorio repositorio;
	private final IRolesRepositorio rolesRepositorio;
	private final IDepartamentosRepositorio departamentosRepositorio;

	public UsuariosUseCaseImpl(IUsuariosRepositorio repositorio, IRolesRepositorio rolesRepositorio,
			IDepartamentosRepositorio departamentosRepositorio) {
		this.repositorio = repositorio;
		this.rolesRepositorio = rolesRepositorio;
		this.departamentosRepositorio = departamentosRepositorio;
	}

	@Override
	public Usuarios crear(Usuarios usuario) {
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			if (rolesRepositorio.buscarPorId(idRol).isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			if (departamentosRepositorio.buscarPorId(idDep).isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
		}

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

	@Override
	public Usuarios actualizar(Usuarios usuario) {
		if (usuario.getFkRol() != null) {
			int idRol = usuario.getFkRol().getIdRol();
			if (rolesRepositorio.buscarPorId(idRol).isEmpty()) {
				throw new RuntimeException("Rol no encontrado");
			}
		}
		if (usuario.getFkDepartamento() != null) {
			int idDep = usuario.getFkDepartamento().getIdDepartamento();
			if (departamentosRepositorio.buscarPorId(idDep).isEmpty()) {
				throw new RuntimeException("Departamento no encontrado");
			}
		}

		return repositorio.guardar(usuario);
	}

}
