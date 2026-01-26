package com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "custodios")
public class CustodiosJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custodio")
    private int idCustodio;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "cedula", length = 20, nullable = false)
    private String cedula;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    private boolean estado;
}
