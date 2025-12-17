package com.uisrael.gestionactivosapi.infraestructura.persistencia.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "equipos")
public class EquiposJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private int idEquipo;

    @Column(name = "codigo_sap", length = 20)
    private String codigoSap;

    @Column(name = "id_categoria", nullable = false)
    private int idCategoria;

    @Column(name = "id_marca", nullable = false)
    private int idMarca;

    @Column(name = "id_proveedor", nullable = false)
    private int idProveedor;

    @Column(name = "tipo_equipo", length = 100)
    private String tipoEquipo;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "serial", length = 100)
    private String serial;

    @Column(name = "procesador", length = 100)
    private String procesador;

    @Column(name = "memoria_ram_gb")
    private Integer memoriaRamGb;

    @Column(name = "capacidad_almacenamiento_gb")
    private Integer capacidadAlmacenamientoGb;

    @Column(name = "sistema_operativo", length = 100)
    private String sistemaOperativo;

    @Column(name = "licencia_windows_activada")
    private Boolean licenciaWindowsActivada;

    @Column(name = "etiqueta_activo_fijo")
    private Boolean etiquetaActivoFijo;

    @Column(name = "tipo_licencia_office", length = 50)
    private String tipoLicenciaOffice;

    @Column(name = "version_office", length = 150)
    private String versionOffice;

    @Column(name = "union_dominio")
    private Boolean unionDominio;

    @Column(name = "ip", length = 100)
    private String ip;

    @Column(name = "mac", length = 100)
    private String mac;

    @Column(name = "id_departamento")
    private Integer idDepartamento;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "estado_equipo", length = 50)
    private String estadoEquipo;

    @Column(name = "observacion_equipo", columnDefinition = "TEXT")
    private String observacionEquipo;

    @Column(name = "estado", length = 20)
    private String estado;
}
