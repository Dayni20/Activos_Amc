package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;

public interface IEquiposUseCase {
    Equipos crear(Equipos equipo);
    List<Equipos> listar();
    Equipos obtenerPorId(int id);

    Equipos actualizar(int id, Equipos equipo);
    Equipos actualizarEstado(int id, Equipos equipo);
}
