package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IDepartamentosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Departamentos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IDepartamentosRepositorio;

public class DepartamentosUseCaseImpl implements IDepartamentosUseCase {

	private final IDepartamentosRepositorio repositorio;

	public DepartamentosUseCaseImpl(IDepartamentosRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public Departamentos crear(Departamentos departamento) {
		return repositorio.guardar(departamento);
	}

	@Override
	public Departamentos obtenerPorId(int id) {
		return repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
	}

	@Override
	public List<Departamentos> listar() {
		return repositorio.listarTodos();
	}

	@Override
	public Departamentos actualizar(int id, Departamentos departamento) {
		repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

		Departamentos actualizado = new Departamentos(id, departamento.getNombre(), departamento.isEstado(),
				departamento.getFkUbicacion());

		return repositorio.actualizar(id, actualizado);
	}

	@Override
	public Departamentos actualizarEstado(int id, boolean estado) {

		Departamentos actual = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

		// ✅ Solo cambia el estado, mantiene nombre/agencia
		Departamentos actualizado = new Departamentos(actual.getIdDepartamento(), // o id, según tu constructor
				actual.getNombre(), estado, actual.getFkUbicacion());

		return repositorio.actualizarEstado(id, actualizado);
	}

}
