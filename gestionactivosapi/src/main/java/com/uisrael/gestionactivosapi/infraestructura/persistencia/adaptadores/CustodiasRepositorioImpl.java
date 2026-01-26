package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.dominio.repositorios.ICustodiasRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
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

        // ✅ setear relaciones por ID
        if (custodia.getFkEquipo() != null) {
            EquiposJpa eq = new EquiposJpa();
            eq.setIdEquipo(custodia.getFkEquipo().getIdEquipo());
            entity.setFkEquipo(eq);
        }

        if (custodia.getFkCustodio() != null) {
            CustodiosJpa cu = new CustodiosJpa();
            cu.setIdCustodio(custodia.getFkCustodio().getIdCustodio());
            entity.setFkCustodio(cu);
        }

        CustodiasJpa guardado = jpaRepository.save(entity);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Optional<Custodias> buscarPorId(int id) {
        return jpaRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public List<Custodias> listarTodos() {
        return jpaRepository.findAll().stream().map(entityMapper::toDomain).toList();
    }

    @Override
    public Custodias actualizar(int id, Custodias custodia) {
        CustodiasJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custodia no encontrada"));

        existente.setFechaInicio(custodia.getFechaInicio());
        existente.setFechaFin(custodia.getFechaFin());
        existente.setObservacion(custodia.getObservacion());
        existente.setEstado(custodia.isEstado());

        // ✅ actualizar relaciones por ID si vienen
        if (custodia.getFkEquipo() != null) {
            EquiposJpa eq = new EquiposJpa();
            eq.setIdEquipo(custodia.getFkEquipo().getIdEquipo());
            existente.setFkEquipo(eq);
        }

        if (custodia.getFkCustodio() != null) {
            CustodiosJpa cu = new CustodiosJpa();
            cu.setIdCustodio(custodia.getFkCustodio().getIdCustodio());
            existente.setFkCustodio(cu);
        }

        CustodiasJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Custodias actualizarEstado(int id, Custodias custodia) {
        CustodiasJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custodia no encontrada"));

        existente.setEstado(custodia.isEstado());

        CustodiasJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }
}
