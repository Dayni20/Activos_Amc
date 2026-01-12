package com.uisrael.gestionactivosapi.dominio.repositorios;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;

public interface ICustodiasRepositorio {

    Custodias guardar(Custodias custodia);

    Optional<Custodias> buscarPorId(int id);

    List<Custodias> listarTodos();

}
