package com.uisrael.gestionactivosapi.aplicacion.casosuso.entradas;

import java.util.List;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;

public interface ICustodiasUseCase {

    Custodias crear(Custodias custodia);

    Custodias obtenerPorId(int id);

    List<Custodias> listar();

}
