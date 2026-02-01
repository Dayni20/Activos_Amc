package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.CategoriaEquipos;
import com.uisrael.gestionactivosapi.dominio.entidades.Departamentos;
import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.EquiposRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.EquiposResponseDTO;

@Mapper(componentModel = "spring")
public interface IEquiposDtoMapper {

    // REQUEST DTO -> DOMINIO
    @Mapping(target = "fkDepartamento", expression = "java(mapDepartamento(dto))")
    @Mapping(target = "fkCategoria", expression = "java(mapCategoria(dto))")
    @Mapping(target = "fkMarca", expression = "java(mapMarca(dto))")
    @Mapping(target = "fkProveedor", expression = "java(mapProveedor(dto))")
    Equipos toDomain(EquiposRequestDTO dto);

    // DOMINIO -> RESPONSE DTO
    EquiposResponseDTO toResponseDto(Equipos equipo);

    // ====== MAPS MANUALES (solo ID para relaciones) ======

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

        // Ajusta si tu constructor de CategoriaEquipos es distinto
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

        // Si tu constructor Proveedores requiere más campos, igual puedes pasar nulls/true.
        // Ajusta según tu clase real.
        return new Proveedores(
            dto.getFkProveedor().getIdProveedor(),
            null,
            null,
            null,
            null,
            null,
            true
        );
    }
}
