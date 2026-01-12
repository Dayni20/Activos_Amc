package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.IEquiposJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.IEquiposJpaRepositorio;

public class EquiposRepositorioImpl implements IEquiposRepositorio {

    private final IEquiposJpaRepositorio jpaRepository;
    private final IEquiposJpaMapper entityMapper;

    public EquiposRepositorioImpl(IEquiposJpaRepositorio jpaRepository,
                                  IEquiposJpaMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Equipos guardar(Equipos equipo) {
        EquiposJpa entity = entityMapper.toEntity(equipo);
        EquiposJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Equipos> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Equipos> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}
