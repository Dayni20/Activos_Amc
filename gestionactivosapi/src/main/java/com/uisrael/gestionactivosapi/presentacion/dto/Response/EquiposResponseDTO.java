package com.uisrael.gestionactivosapi.presentacion.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EquiposResponseDTO {

    private int id_equipo;
    private String codigo_sap;
    private int id_categoria;
    private int id_marca;
    private int id_proveedor;
    private String tipo_equipo;
    private String modelo;
    private String serial;
    private String procesador;
    private Integer memoria_ram_gb;
    private Integer capacidad_almacenamiento_gb;
    private String sistema_operativo;
    private Boolean licencia_windows_activada;
    private Boolean etiqueta_activo_fijo;
    private String tipo_licencia_office;
    private String version_office;
    private Boolean union_dominio;
    private String ip;
    private String mac;
    private Integer id_departamento;
    private LocalDate fecha_compra;
    private BigDecimal precio_compra;
    private String estado_equipo;
    private String observacion_equipo;
    private String estado;

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getCodigo_sap() {
        return codigo_sap;
    }

    public void setCodigo_sap(String codigo_sap) {
        this.codigo_sap = codigo_sap;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(String tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public Integer getMemoria_ram_gb() {
        return memoria_ram_gb;
    }

    public void setMemoria_ram_gb(Integer memoria_ram_gb) {
        this.memoria_ram_gb = memoria_ram_gb;
    }

    public Integer getCapacidad_almacenamiento_gb() {
        return capacidad_almacenamiento_gb;
    }

    public void setCapacidad_almacenamiento_gb(Integer capacidad_almacenamiento_gb) {
        this.capacidad_almacenamiento_gb = capacidad_almacenamiento_gb;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }

    public Boolean getLicencia_windows_activada() {
        return licencia_windows_activada;
    }

    public void setLicencia_windows_activada(Boolean licencia_windows_activada) {
        this.licencia_windows_activada = licencia_windows_activada;
    }

    public Boolean getEtiqueta_activo_fijo() {
        return etiqueta_activo_fijo;
    }

    public void setEtiqueta_activo_fijo(Boolean etiqueta_activo_fijo) {
        this.etiqueta_activo_fijo = etiqueta_activo_fijo;
    }

    public String getTipo_licencia_office() {
        return tipo_licencia_office;
    }

    public void setTipo_licencia_office(String tipo_licencia_office) {
        this.tipo_licencia_office = tipo_licencia_office;
    }

    public String getVersion_office() {
        return version_office;
    }

    public void setVersion_office(String version_office) {
        this.version_office = version_office;
    }

    public Boolean getUnion_dominio() {
        return union_dominio;
    }

    public void setUnion_dominio(Boolean union_dominio) {
        this.union_dominio = union_dominio;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(Integer id_departamento) {
        this.id_departamento = id_departamento;
    }

    public LocalDate getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDate fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(BigDecimal precio_compra) {
        this.precio_compra = precio_compra;
    }

    public String getEstado_equipo() {
        return estado_equipo;
    }

    public void setEstado_equipo(String estado_equipo) {
        this.estado_equipo = estado_equipo;
    }

    public String getObservacion_equipo() {
        return observacion_equipo;
    }

    public void setObservacion_equipo(String observacion_equipo) {
        this.observacion_equipo = observacion_equipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
