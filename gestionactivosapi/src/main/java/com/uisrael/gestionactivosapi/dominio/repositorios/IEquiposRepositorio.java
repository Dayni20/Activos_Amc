package com.uisrael.gestionactivosapi.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;

public interface IEquiposRepositorio {

    Equipos guardar(Equipos equipo);

    Optional<Equipos> buscarPorId(int id);

    List<Equipos> listarTodos();

}
