package com.uisrael.gestionactivosapi.aplicacion.casosuso.impl;

import java.util.List;

import com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas.ICustodiasUseCase;
import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiasRepositorio;

public class CustodiasUseCaseImpl implements ICustodiasUseCase {

    private final ICustodiasRepositorio repositorio;

    public CustodiasUseCaseImpl(ICustodiasRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Custodias crear(Custodias custodia) {
        return repositorio.guardar(custodia);
    }

    @Override
    public Custodias obtenerPorId(int id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodia no encontrada"));
    }

    @Override
    public List<Custodias> listar() {
        return repositorio.listarTodos();
    }

    @Override
    public Custodias actualizar(int id, Custodias custodia) {
        repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodia no encontrada"));
        return repositorio.actualizar(id, custodia);
    }

    @Override
    public Custodias actualizarEstado(int id, Custodias custodia) {
        repositorio.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Custodia no encontrada"));
        return repositorio.actualizarEstado(id, custodia);
    }
}
