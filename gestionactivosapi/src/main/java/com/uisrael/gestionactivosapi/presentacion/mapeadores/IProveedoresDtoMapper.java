package com.uisrael.gestionactivosapi.presentacion.mapeadores;

import org.mapstruct.Mapper;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.presentacion.dto.Request.ProveedoresRequestDTO;
import com.uisrael.gestionactivosapi.presentacion.dto.Response.ProveedoresResponseDTO;

@Mapper(componentModel = "spring")
public interface IProveedoresDtoMapper {


    default Proveedores toDomain(ProveedoresRequestDTO dto) {
        return Proveedores.of(
                dto.getId_proveedor(),
                dto.getNombre(),
                dto.getRuc(),
                dto.getTelefono(),
                dto.getCorreo(),
                dto.getDireccion(),
                dto.getEstado()
        );
    }

   
    ProveedoresResponseDTO toResponseDto(Proveedores proveedor);
}