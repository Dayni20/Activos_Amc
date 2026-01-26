package com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "custodias")
public class CustodiasJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custodia_equipo")
    private int idCustodiaEquipo;

    @ManyToOne
    @JoinColumn(name = "id_equipo")   // <-- AJUSTA al nombre real en tu tabla
    private EquiposJpa fkEquipo;

    @ManyToOne
    @JoinColumn(name = "id_custodio") // <-- AJUSTA al nombre real en tu tabla
    private CustodiosJpa fkCustodio;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    private String observacion;

    private boolean estado;
}
