package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodios;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiosRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiosJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiosJpaRepositorio;

public class CustodiosRepositorioImpl implements ICustodiosRepositorio {

    private final ICustodiosJpaRepositorio jpaRepository;
    private final ICustodiosJpaMapper entityMapper;

    public CustodiosRepositorioImpl(ICustodiosJpaRepositorio jpaRepository,
                                    ICustodiosJpaMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Custodios guardar(Custodios custodio) {
        CustodiosJpa entity = entityMapper.toEntity(custodio);
        CustodiosJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Custodios> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Custodios> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}
