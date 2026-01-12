package com.uisrael.gestionactivosapi.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;

public interface ICustodiosRepositorio {

    Custodios guardar(Custodios custodio);

    Optional<Custodios> buscarPorId(int id);

    List<Custodios> listarTodos();

}
