package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;

@Mapper(componentModel = "spring")
public interface IProveedoresJpaMapper {

    default Proveedores toDomain(ProveedoresJpa entity) {
        if (entity == null) return null;

        return Proveedores.of(
                entity.getIdProveedor(),
                entity.getNombre(),
                entity.getRuc(),
                entity.getTelefono(),
                entity.getCorreo(),
                entity.getDireccion(),
                entity.getEstado()
        );
    }

    @Mapping(target = "idProveedor", source = "id_proveedor")
    ProveedoresJpa toEntity(Proveedores proveedor);
}
