package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiasRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores.ICustodiasJpaMapper;
import com.uisrael.gestionactivosapi.infraestructura.repositorios.ICustodiasJpaRepositorio;

public class CustodiasRepositorioImpl implements ICustodiasRepositorio {

    private final ICustodiasJpaRepositorio jpaRepository;
    private final ICustodiasJpaMapper entityMapper;

    public CustodiasRepositorioImpl(ICustodiasJpaRepositorio jpaRepository,
                                    ICustodiasJpaMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Custodias guardar(Custodias custodia) {
        CustodiasJpa entity = entityMapper.toEntity(custodia);
        CustodiasJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Custodias> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Custodias> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }
}
