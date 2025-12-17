package com.uisrael.gestionactivosapi.dominio.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Equipos implements Serializable {

    private static final long serialVersionUID = 1L;

    // PK
    private final int id_equipo;

    // Identificación
    private final String codigo_sap;

    // Catálogos (FK)
    private final int id_categoria;
    private final int id_marca;
    private final int id_proveedor;

    // Datos del equipo
    private final String tipo_equipo;
    private final String modelo;
    private final String serial;
    private final String procesador;
    private final Integer memoria_ram_gb;
    private final Integer capacidad_almacenamiento_gb;
    private final String sistema_operativo;
    private final Boolean licencia_windows_activada;
    private final Boolean etiqueta_activo_fijo;
    private final String tipo_licencia_office;
    private final String version_office;
    private final Boolean union_dominio;
    private final String ip;
    private final String mac;

    // Ubicación organizacional
    private final Integer id_departamento;

    // Compra
    private final LocalDate fecha_compra;
    private final BigDecimal precio_compra;

    // Estado del equipo
    private final String estado_equipo;
    private final String observacion_equipo;

    // Estado lógico
    private final String estado;

    private Equipos(
            int id_equipo,
            String codigo_sap,
            int id_categoria,
            int id_marca,
            int id_proveedor,
            String tipo_equipo,
            String modelo,
            String serial,
            String procesador,
            Integer memoria_ram_gb,
            Integer capacidad_almacenamiento_gb,
            String sistema_operativo,
            Boolean licencia_windows_activada,
            Boolean etiqueta_activo_fijo,
            String tipo_licencia_office,
            String version_office,
            Boolean union_dominio,
            String ip,
            String mac,
            Integer id_departamento,
            LocalDate fecha_compra,
            BigDecimal precio_compra,
            String estado_equipo,
            String observacion_equipo,
            String estado
    ) {
        this.id_equipo = id_equipo;
        this.codigo_sap = codigo_sap;
        this.id_categoria = id_categoria;
        this.id_marca = id_marca;
        this.id_proveedor = id_proveedor;
        this.tipo_equipo = tipo_equipo;
        this.modelo = modelo;
        this.serial = serial;
        this.procesador = procesador;
        this.memoria_ram_gb = memoria_ram_gb;
        this.capacidad_almacenamiento_gb = capacidad_almacenamiento_gb;
        this.sistema_operativo = sistema_operativo;
        this.licencia_windows_activada = licencia_windows_activada;
        this.etiqueta_activo_fijo = etiqueta_activo_fijo;
        this.tipo_licencia_office = tipo_licencia_office;
        this.version_office = version_office;
        this.union_dominio = union_dominio;
        this.ip = ip;
        this.mac = mac;
        this.id_departamento = id_departamento;
        this.fecha_compra = fecha_compra;
        this.precio_compra = precio_compra;
        this.estado_equipo = estado_equipo;
        this.observacion_equipo = observacion_equipo;
        this.estado = estado;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public String getCodigo_sap() {
        return codigo_sap;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public int getId_marca() {
        return id_marca;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public String getTipo_equipo() {
        return tipo_equipo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerial() {
        return serial;
    }

    public String getProcesador() {
        return procesador;
    }

    public Integer getMemoria_ram_gb() {
        return memoria_ram_gb;
    }

    public Integer getCapacidad_almacenamiento_gb() {
        return capacidad_almacenamiento_gb;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public Boolean getLicencia_windows_activada() {
        return licencia_windows_activada;
    }

    public Boolean getEtiqueta_activo_fijo() {
        return etiqueta_activo_fijo;
    }

    public String getTipo_licencia_office() {
        return tipo_licencia_office;
    }

    public String getVersion_office() {
        return version_office;
    }

    public Boolean getUnion_dominio() {
        return union_dominio;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public Integer getId_departamento() {
        return id_departamento;
    }

    public LocalDate getFecha_compra() {
        return fecha_compra;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public String getEstado_equipo() {
        return estado_equipo;
    }

    public String getObservacion_equipo() {
        return observacion_equipo;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Equipos [" +
                "id_equipo=" + id_equipo +
                ", codigo_sap=" + codigo_sap +
                ", id_categoria=" + id_categoria +
                ", id_marca=" + id_marca +
                ", id_proveedor=" + id_proveedor +
                ", tipo_equipo=" + tipo_equipo +
                ", modelo=" + modelo +
                ", serial=" + serial +
                ", procesador=" + procesador +
                ", memoria_ram_gb=" + memoria_ram_gb +
                ", capacidad_almacenamiento_gb=" + capacidad_almacenamiento_gb +
                ", sistema_operativo=" + sistema_operativo +
                ", licencia_windows_activada=" + licencia_windows_activada +
                ", etiqueta_activo_fijo=" + etiqueta_activo_fijo +
                ", tipo_licencia_office=" + tipo_licencia_office +
                ", version_office=" + version_office +
                ", union_dominio=" + union_dominio +
                ", ip=" + ip +
                ", mac=" + mac +
                ", id_departamento=" + id_departamento +
                ", fecha_compra=" + fecha_compra +
                ", precio_compra=" + precio_compra +
                ", estado_equipo=" + estado_equipo +
                ", observacion_equipo=" + observacion_equipo +
                ", estado=" + estado +
                "]";
    }
}
