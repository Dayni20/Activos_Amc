package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.*;

import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.*;

@Mapper(componentModel = "spring")
public interface ICustodiasDtoMapper {

    // =========================
    // REQUEST -> DOMAIN
    // =========================
    @Mapping(target = "fkEquipo", expression = "java(mapEquipoReq(dto))")
    @Mapping(target = "fkCustodio", expression = "java(mapCustodioReq(dto))")
    Custodias toDomain(CustodiasRequestDTO dto);

    // =========================
    // DOMAIN -> RESPONSE
    // =========================
    CustodiasResponseDTO toResponseDto(Custodias custodia);

    // =========================
    // Helpers Request -> Domain (solo IDs)
    // =========================
    default EquiposJpa mapEquipoReq(CustodiasRequestDTO dto) {
        if (dto == null || dto.getFkEquipo() == null) return null;
        EquiposJpa e = new EquiposJpa();
        e.setIdEquipo(dto.getFkEquipo().getIdEquipo());
        return e;
    }

    default CustodiosJpa mapCustodioReq(CustodiasRequestDTO dto) {
        if (dto == null || dto.getFkCustodio() == null) return null;
        CustodiosJpa c = new CustodiosJpa();
        c.setIdCustodio(dto.getFkCustodio().getIdCustodio());
        return c;
    }

    // =========================
    // ✅ JPA -> RESPONSE (COMPLETO)
    // =========================
    default EquiposResponseDTO map(EquiposJpa e) {
        if (e == null) return null;

        EquiposResponseDTO dto = new EquiposResponseDTO();

        dto.setIdEquipo(e.getIdEquipo());
        dto.setCodigoSap(e.getCodigoSap());
        dto.setTipoEquipo(e.getTipoEquipo());
        dto.setModelo(e.getModelo());
        dto.setSerial(e.getSerial());
        dto.setProcesador(e.getProcesador());
        dto.setMemoriaRamGb(e.getMemoriaRamGb());
        dto.setCapacidadAlmacenamientoGb(e.getCapacidadAlmacenamientoGb());
        dto.setSistemaOperativo(e.getSistemaOperativo());
        dto.setLicenciaWindowsActivada(e.getLicenciaWindowsActivada());
        dto.setEtiquetaActivoFijo(e.getEtiquetaActivoFijo());
        dto.setTipoLicenciaOffice(e.getTipoLicenciaOffice());
        dto.setVersionOffice(e.getVersionOffice());
        dto.setUnionDominio(e.getUnionDominio());
        dto.setIp(e.getIp());
        dto.setMac(e.getMac());
        dto.setFechaCompra(e.getFechaCompra());
        dto.setPrecioCompra(e.getPrecioCompra());
        dto.setEstadoEquipo(e.getEstadoEquipo());
        dto.setObservacionEquipo(e.getObservacionEquipo());
        dto.setEstado(e.isEstado());

        // ✅ FKs (para que en el JSON salga NOMBRE y no solo id)
        dto.setFkMarca(map(e.getFkMarcas()));
        dto.setFkProveedor(map(e.getFkProveedor()));
        dto.setFkCategoria(map(e.getFkCategoria()));

        return dto;
    }

    default CustodiosResponseDTO map(CustodiosJpa c) {
        if (c == null) return null;

        CustodiosResponseDTO dto = new CustodiosResponseDTO();
        dto.setIdCustodio(c.getIdCustodio());
        dto.setNombre(c.getNombre());
        dto.setCedula(c.getCedula());
        dto.setCorreo(c.getCorreo());
        dto.setTelefono(c.getTelefono());
        dto.setEstado(c.isEstado());
        return dto;
    }

    // ====== MAPS de FKs de Equipos ======

    default MarcasResponseDTO map(MarcasJpa m) {
        if (m == null) return null;
        MarcasResponseDTO dto = new MarcasResponseDTO();
        dto.setIdMarca(m.getIdMarca());
        dto.setNombre(m.getNombre());
        dto.setEstado(m.isEstado());
        return dto;
    }

    default ProveedoresResponseDTO map(ProveedoresJpa p) {
        if (p == null) return null;
        ProveedoresResponseDTO dto = new ProveedoresResponseDTO();
        dto.setIdProveedor(p.getIdProveedor());
        dto.setNombre(p.getNombre());
        dto.setRuc(p.getRuc());
        dto.setTelefono(p.getTelefono());
        dto.setCorreo(p.getCorreo());
        dto.setDireccion(p.getDireccion());
        dto.setEstado(p.isEstado());
        return dto;
    }

    default CategoriaEquiposResponseDTO map(CategoriaEquiposJpa c) {
        if (c == null) return null;
        CategoriaEquiposResponseDTO dto = new CategoriaEquiposResponseDTO();
        dto.setIdCategoria(c.getIdCategoria());
        dto.setNombre(c.getNombre());
        dto.setEstado(c.isEstado());
        return dto;
    }
}
