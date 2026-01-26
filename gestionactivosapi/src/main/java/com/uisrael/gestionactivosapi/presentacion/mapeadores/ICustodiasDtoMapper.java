package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Custodias;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.CustodiosJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.CustodiasRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.CustodiasResponseDTO;

@Mapper(componentModel = "spring")
public interface ICustodiasDtoMapper {

    // ✅ Construye las FKs (JPA) usando SOLO los IDs que vienen en el DTO
    @Mapping(target = "fkEquipo", expression = "java(mapEquipo(dto))")
    @Mapping(target = "fkCustodio", expression = "java(mapCustodio(dto))")
    Custodias toDomain(CustodiasRequestDTO dto);

    CustodiasResponseDTO toResponseDto(Custodias custodia);

    // -------------------------
    // Helpers para MapStruct
    // -------------------------
    default EquiposJpa mapEquipo(CustodiasRequestDTO dto) {
        if (dto == null || dto.getFkEquipo() == null) return null;

        EquiposJpa e = new EquiposJpa();
        e.setIdEquipo(dto.getFkEquipo().getIdEquipo()); // <- debe existir en EquiposRequestDTO
        return e;
    }

    default CustodiosJpa mapCustodio(CustodiasRequestDTO dto) {
        if (dto == null || dto.getFkCustodio() == null) return null;

        CustodiosJpa c = new CustodiosJpa();
        c.setIdCustodio(dto.getFkCustodio().getIdCustodio()); // <- debe existir en CustodiosRequestDTO
        return c;
    }
}
