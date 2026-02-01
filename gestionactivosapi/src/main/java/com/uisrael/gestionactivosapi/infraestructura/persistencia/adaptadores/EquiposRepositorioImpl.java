package com.uisrael.gestionactivosapi.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.repositorios.IEquiposRepositorio;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.DepartamentosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;
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

        // ✅ FK Departamento
        if (equipo.getFkDepartamento() != null) {
            DepartamentosJpa dep = new DepartamentosJpa();
            dep.setIdDepartamento(equipo.getFkDepartamento().getIdDepartamento());
            entity.setFkDepartamento(dep);
        }

        // ✅ FK Marca (en JPA se llama fkMarcas)
        if (equipo.getFkMarca() != null) {
            MarcasJpa marca = new MarcasJpa();
            marca.setIdMarca(equipo.getFkMarca().getIdMarca());
            entity.setFkMarcas(marca);
        }

        // ✅ FK Proveedor (en JPA se llama proveedor)
        if (equipo.getFkProveedor() != null) {
            ProveedoresJpa prov = new ProveedoresJpa();
            prov.setIdProveedor(equipo.getFkProveedor().getIdProveedor());
            entity.setFkProveedor(prov);
        }

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

    @Override
    public Equipos actualizar(int id, Equipos equipo) {
        EquiposJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        existente.setCodigoSap(equipo.getCodigoSap());
        existente.setTipoEquipo(equipo.getTipoEquipo());
        existente.setModelo(equipo.getModelo());
        existente.setSerial(equipo.getSerial());
        existente.setProcesador(equipo.getProcesador());
        existente.setMemoriaRamGb(equipo.getMemoriaRamGb());
        existente.setCapacidadAlmacenamientoGb(equipo.getCapacidadAlmacenamientoGb());
        existente.setSistemaOperativo(equipo.getSistemaOperativo());
        existente.setLicenciaWindowsActivada(equipo.getLicenciaWindowsActivada());
        existente.setEtiquetaActivoFijo(equipo.getEtiquetaActivoFijo());
        existente.setTipoLicenciaOffice(equipo.getTipoLicenciaOffice());
        existente.setVersionOffice(equipo.getVersionOffice());
        existente.setUnionDominio(equipo.getUnionDominio());
        existente.setIp(equipo.getIp());
        existente.setMac(equipo.getMac());
        existente.setFechaCompra(equipo.getFechaCompra());
        existente.setPrecioCompra(equipo.getPrecioCompra());
        existente.setEstadoEquipo(equipo.getEstadoEquipo());
        existente.setObservacionEquipo(equipo.getObservacionEquipo());
        existente.setEstado(equipo.isEstado());

        // ✅ FK Departamento
        if (equipo.getFkDepartamento() != null) {
            DepartamentosJpa dep = new DepartamentosJpa();
            dep.setIdDepartamento(equipo.getFkDepartamento().getIdDepartamento());
            existente.setFkDepartamento(dep);
        }

        // ✅ FK Marca (fkMarcas)
        if (equipo.getFkMarca() != null) {
            MarcasJpa marca = new MarcasJpa();
            marca.setIdMarca(equipo.getFkMarca().getIdMarca());
            existente.setFkMarcas(marca);
        }

        // ✅ FK Proveedor (proveedor)
        if (equipo.getFkProveedor() != null) {
            ProveedoresJpa prov = new ProveedoresJpa();
            prov.setIdProveedor(equipo.getFkProveedor().getIdProveedor());
            existente.setFkProveedor(prov);
        }

        EquiposJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

    @Override
    public Equipos actualizarEstado(int id, boolean estado) {
        EquiposJpa existente = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        existente.setEstado(estado);

        EquiposJpa guardado = jpaRepository.save(existente);
        return entityMapper.toDomain(guardado);
    }

}
