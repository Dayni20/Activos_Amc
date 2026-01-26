package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiosUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiosRepositorio;

public class CustodiosUseCaseImpl implements ICustodiosUseCase {

    private final ICustodiosRepositorio repositorio;

    public CustodiosUseCaseImpl(ICustodiosRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Custodios crear(Custodios custodio) {
        return repositorio.guardar(custodio);
    }

    @Override
    public Custodios obtenerPorId(int id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodio no encontrado"));
    }

    @Override
    public List<Custodios> listar() {
        return repositorio.listarTodos();
    }

    @Override
    public Custodios actualizar(int id, Custodios custodio) {
        repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodio no encontrado"));

        Custodios actualizado = new Custodios(
                id,
                custodio.getNombre(),
                custodio.getCedula(),
                custodio.getCorreo(),
                custodio.getTelefono(),
                custodio.isEstado()
        );

        return repositorio.actualizar(id, actualizado);
    }

    @Override
    public Custodios actualizarEstado(int id, Custodios custodio) {
        repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodio no encontrado"));

        Custodios actualizado = new Custodios(
                id,
                custodio.getNombre(),
                custodio.getCedula(),
                custodio.getCorreo(),
                custodio.getTelefono(),
                custodio.isEstado()
        );

        return repositorio.actualizarEstado(id, actualizado);
    }
}
