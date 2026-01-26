package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.IEquiposUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;

public class EquiposUseCaseImpl implements IEquiposUseCase {

    private final IEquiposRepositorio repositorio;

    public EquiposUseCaseImpl(IEquiposRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Equipos crear(Equipos equipo) {
        return repositorio.guardar(equipo);
    }

    @Override
    public Equipos obtenerPorId(int id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    @Override
    public List<Equipos> listar() {
        return repositorio.listarTodos();
    }
    
    @Override
    public Equipos actualizar(int id, Equipos equipo) {
        return repositorio.actualizar(id, equipo);
    }

    @Override
    public Equipos actualizarEstado(int id, Equipos equipo) {
        return repositorio.actualizarEstado(id, equipo.isEstado());
    }

}
