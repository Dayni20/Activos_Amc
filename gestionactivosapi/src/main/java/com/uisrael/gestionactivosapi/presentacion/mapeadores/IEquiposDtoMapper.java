package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;
import com.uisrael.gestionactivosapi.dominio.entidades.Departamentos;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;

import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CategoriaEquiposResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.DepartamentosResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.MarcasResponseDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.ProveedoresResponseDTO;

@Mapper(componentModel = "spring")
public interface IEquiposDtoMapper {

    // =========================
    // REQUEST DTO -> DOMINIO
    // =========================
    @Mapping(target = "fkDepartamento", expression = "java(mapDepartamento(dto))")
    @Mapping(target = "fkCategoria", expression = "java(mapCategoria(dto))")
    @Mapping(target = "fkMarca", expression = "java(mapMarca(dto))")
    @Mapping(target = "fkProveedor", expression = "java(mapProveedor(dto))")
    Equipos toDomain(EquiposRequestDTO dto);

    // =========================
    // DOMINIO -> RESPONSE DTO
    // (✅ aquí está tu arreglo)
    // =========================
    @Mapping(target = "fkDepartamento", expression = "java(toDepartamentoResponse(equipo.getFkDepartamento()))")
    @Mapping(target = "fkMarca", expression = "java(toMarcaResponse(equipo.getFkMarca()))")
    @Mapping(target = "fkProveedor", expression = "java(toProveedorResponse(equipo.getFkProveedor()))")
    @Mapping(target = "fkCategoria", expression = "java(toCategoriaResponse(equipo.getFkCategoria()))")
    EquiposResponseDTO toResponseDto(Equipos equipo);

    // ==========================================================
    // MAPS MANUALES (REQUEST -> DOMINIO)  (solo ID en relaciones)
    // ==========================================================
    default Departamentos mapDepartamento(EquiposRequestDTO dto) {
        if (dto == null || dto.getFkDepartamento() == null) return null;
        return new Departamentos(
                dto.getFkDepartamento().getIdDepartamento(),
                null,
                true,
                null
        );
    }

    default CategoriaEquipos mapCategoria(EquiposRequestDTO dto) {
        if (dto == null || dto.getFkCategoria() == null) return null;
        return new CategoriaEquipos(
                dto.getFkCategoria().getIdCategoria(),
                null,
                true
        );
    }

    default Marcas mapMarca(EquiposRequestDTO dto) {
        if (dto == null || dto.getFkMarca() == null) return null;
        return new Marcas(
                dto.getFkMarca().getIdMarca(),
                null,
                true
        );
    }

    default Proveedores mapProveedor(EquiposRequestDTO dto) {
        if (dto == null || dto.getFkProveedor() == null) return null;
        return new Proveedores(
                dto.getFkProveedor().getIdProveedor(),
                null, null, null, null, null,
                true
        );
    }

    // ==========================================================
    // MAPS MANUALES (DOMINIO -> RESPONSE) (✅ para que salga NOMBRE)
    // ==========================================================
    default DepartamentosResponseDTO toDepartamentoResponse(Departamentos d) {
        if (d == null) return null;
        DepartamentosResponseDTO r = new DepartamentosResponseDTO();
        r.setIdDepartamento(d.getIdDepartamento());
        r.setNombre(d.getNombre());
        r.setEstado(d.isEstado());
        r.setFkUbicacion(null); // si quieres incluir ubicación, aquí ajustas
        return r;
    }

    default MarcasResponseDTO toMarcaResponse(Marcas m) {
        if (m == null) return null;
        MarcasResponseDTO r = new MarcasResponseDTO();
        r.setIdMarca(m.getIdMarca());
        r.setNombre(m.getNombre());
        r.setEstado(m.isEstado());
        return r;
    }

    default ProveedoresResponseDTO toProveedorResponse(Proveedores p) {
        if (p == null) return null;
        ProveedoresResponseDTO r = new ProveedoresResponseDTO();
        r.setIdProveedor(p.getIdProveedor());
        r.setNombre(p.getNombre());
        r.setEstado(p.isEstado());
        // si tu ResponseDTO tiene más campos, setéalos aquí
        return r;
    }

    default CategoriaEquiposResponseDTO toCategoriaResponse(CategoriaEquipos c) {
        if (c == null) return null;
        CategoriaEquiposResponseDTO r = new CategoriaEquiposResponseDTO();
        r.setIdCategoria(c.getIdCategoria());
        r.setNombre(c.getNombre());
        r.setEstado(c.isEstado());
        return r;
    }
}
