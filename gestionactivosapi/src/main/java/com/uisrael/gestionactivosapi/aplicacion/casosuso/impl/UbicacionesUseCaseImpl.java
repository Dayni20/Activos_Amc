package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IUbicacionesUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Ubicaciones;
import com.uisrael.gestionactivosapi.dominio.repositorios.IUbicacionesRepositorio;

public class UbicacionesUseCaseImpl implements IUbicacionesUseCase {

	private final IUbicacionesRepositorio repositorio;

	public UbicacionesUseCaseImpl(IUbicacionesRepositorio repositorio) {
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

	@Override
	public Ubicaciones actualizar(int id, Ubicaciones ubicacion) {
		repositorio.buscarPorId(id).orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

		Ubicaciones actualizado = new Ubicaciones(id, ubicacion.getNombre(), ubicacion.getAgencia(),
				ubicacion.isEstado());

		return repositorio.actualizar(id, actualizado);
	}

	@Override
	public Ubicaciones actualizarEstado(int id, boolean estado) {

		Ubicaciones actual = repositorio.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

		// ✅ Solo cambia el estado, mantiene nombre/agencia
		Ubicaciones actualizado = new Ubicaciones(actual.getIdUbicacion(), // o id, según tu constructor
				actual.getNombre(), actual.getAgencia(), estado);

		return repositorio.actualizarEstado(id, actualizado);
	}

	@Override
	public boolean nombreExiste(String nombre) {
		return repositorio.existeNombre(nombre.trim());
	}

	@Override
	public boolean nombreExisteParaOtro(String nombre, Integer idUbicacion) {
		return repositorio.existeNombreParaOtro(nombre.trim(), idUbicacion);
	}

}
