package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;

public interface IEquiposUseCase {

    Equipos crear(Equipos equipo);

    Equipos obtenerPorId(int id);

    List<Equipos> listar();

}
