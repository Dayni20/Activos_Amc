package com.uisrael.gestionactivosapi.infraestructura.persistencia.mapeadores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.uisrael.gestionactivosapi.dominio.entidades.Equipos;
import com.uisrael.gestionactivosapi.dominio.entidades.Marcas;
import com.uisrael.gestionactivosapi.dominio.entidades.Proveedores;

import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.EquiposJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.MarcasJpa;
import com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa.ProveedoresJpa;

@Mapper(componentModel = "spring")
public interface IEquiposJpaMapper {


    @Mapping(source = "fkMarca", target = "fkMarcas")
    @Mapping(source = "fkProveedor", target = "proveedor")
    EquiposJpa toEntity(Equipos equipo);


    @Mapping(source = "fkMarcas", target = "fkMarca")
    @Mapping(source = "proveedor", target = "fkProveedor")
    Equipos toDomain(EquiposJpa entity);



    default MarcasJpa map(Marcas m) {
        if (m == null) return null;
        MarcasJpa j = new MarcasJpa();
        j.setIdMarca(m.getIdMarca());
        return j;
    }

    default Marcas map(MarcasJpa j) {
        if (j == null) return null;
   
        return new Marcas(j.getIdMarca(), j.getNombre(), j.isEstado());
    }

    default ProveedoresJpa map(Proveedores p) {
        if (p == null) return null;
        ProveedoresJpa j = new ProveedoresJpa();
        j.setIdProveedor(p.getIdProveedor());
        return j;
    }

    default Proveedores map(ProveedoresJpa j) {
        if (j == null) return null;
      
        return new Proveedores(
            j.getIdProveedor(),
            j.getNombre(),
            j.getRuc(),
            j.getTelefono(),
            j.getCorreo(),
            j.getDireccion(),
            j.isEstado()
        );
    }
}
