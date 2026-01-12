package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;

public interface ICustodiosUseCase {

    Custodios crear(Custodios custodio);

    Custodios obtenerPorId(int id);

    List<Custodios> listar();

}
