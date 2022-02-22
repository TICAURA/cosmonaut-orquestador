package com.aura.nomina.domain;

import java.time.LocalDate;

public class Empleado {
    private Integer cliente_id;
    private LocalDate fecha_contrato;
    private Integer persona_id;
    private Integer politica_id;
    private Integer grupo_nomina;
    private Integer tipo_compensacion;
    private Integer sbm_imss;
    private LocalDate fec_ini_periodo;

    private Integer param1;
    private Integer param2;

    //f_lista_general_nominas
    private Integer clienteId;
    private Integer tipoNomina;
    private Boolean esActivo;
    private Boolean esExtraordinario;
    private Integer estado;

    //f_lista_empleados_timbres_nomina
    private Integer nominaXperiodoId;
    private Integer estadoTimbre;
    private Boolean metodoPago;
    private Boolean esEstadoError;

    //f_detalle_nomina_empleado
    private LocalDate fechaContrato;
    private Integer personaId;

    private Integer usuarioId;

    private Integer grupoNomina;
    private LocalDate fecIniPeriodo;
    private LocalDate fecFinPeriodo;
    private LocalDate fecIniIncidencia;
    private LocalDate fecFinIncidencia;

    private Integer estatusPago;

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public LocalDate getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(LocalDate fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public Integer getPolitica_id() {
        return politica_id;
    }

    public void setPolitica_id(Integer politica_id) {
        this.politica_id = politica_id;
    }

    public Integer getGrupo_nomina() {
        return grupo_nomina;
    }

    public void setGrupo_nomina(Integer grupo_nomina) {
        this.grupo_nomina = grupo_nomina;
    }

    public Integer getTipo_compensacion() {
        return tipo_compensacion;
    }

    public void setTipo_compensacion(Integer tipo_compensacion) {
        this.tipo_compensacion = tipo_compensacion;
    }

    public Integer getSbm_imss() {
        return sbm_imss;
    }

    public void setSbm_imss(Integer sbm_imss) {
        this.sbm_imss = sbm_imss;
    }

    public LocalDate getFec_ini_periodo() {
        return fec_ini_periodo;
    }

    public void setFec_ini_periodo(LocalDate fec_ini_periodo) {
        this.fec_ini_periodo = fec_ini_periodo;
    }

    public Integer getParam1() {
        return param1;
    }

    public void setParam1(Integer param1) {
        this.param1 = param1;
    }

    public Integer getParam2() {
        return param2;
    }

    public void setParam2(Integer param2) {
        this.param2 = param2;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(Integer tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public Boolean getEsExtraordinario() {
        return esExtraordinario;
    }

    public void setEsExtraordinario(Boolean esExtraordinario) {
        this.esExtraordinario = esExtraordinario;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getNominaXperiodoId() {
        return nominaXperiodoId;
    }

    public void setNominaXperiodoId(Integer nominaXperiodoId) {
        this.nominaXperiodoId = nominaXperiodoId;
    }

    public Integer getEstadoTimbre() {
        return estadoTimbre;
    }

    public void setEstadoTimbre(Integer estadoTimbre) {
        this.estadoTimbre = estadoTimbre;
    }

    public Boolean getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Boolean metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Boolean getEsEstadoError() {
        return esEstadoError;
    }

    public void setEsEstadoError(Boolean esEstadoError) {
        this.esEstadoError = esEstadoError;
    }

    public LocalDate getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(LocalDate fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getGrupoNomina() {
        return grupoNomina;
    }

    public void setGrupoNomina(Integer grupoNomina) {
        this.grupoNomina = grupoNomina;
    }

    public LocalDate getFecIniPeriodo() {
        return fecIniPeriodo;
    }

    public void setFecIniPeriodo(LocalDate fecIniPeriodo) {
        this.fecIniPeriodo = fecIniPeriodo;
    }

    public LocalDate getFecFinPeriodo() {
        return fecFinPeriodo;
    }

    public void setFecFinPeriodo(LocalDate fecFinPeriodo) {
        this.fecFinPeriodo = fecFinPeriodo;
    }

    public LocalDate getFecIniIncidencia() {
        return fecIniIncidencia;
    }

    public void setFecIniIncidencia(LocalDate fecIniIncidencia) {
        this.fecIniIncidencia = fecIniIncidencia;
    }

    public LocalDate getFecFinIncidencia() {
        return fecFinIncidencia;
    }

    public void setFecFinIncidencia(LocalDate fecFinIncidencia) {
        this.fecFinIncidencia = fecFinIncidencia;
    }

    public Integer getEstatusPago() {
        return estatusPago;
    }

    public void setEstatusPago(Integer estatusPago) {
        this.estatusPago = estatusPago;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "cliente_id=" + cliente_id +
                ", fecha_contrato=" + fecha_contrato +
                ", persona_id=" + persona_id +
                ", politica_id=" + politica_id +
                ", grupo_nomina=" + grupo_nomina +
                ", tipo_compensacion=" + tipo_compensacion +
                ", sbm_imss=" + sbm_imss +
                ", fec_ini_periodo=" + fec_ini_periodo +
                ", param1=" + param1 +
                ", param2=" + param2 +
                ", clienteId=" + clienteId +
                ", tipoNomina=" + tipoNomina +
                ", esActivo=" + esActivo +
                ", esExtraordinario=" + esExtraordinario +
                ", estado=" + estado +
                ", nominaXperiodoId=" + nominaXperiodoId +
                ", estadoTimbre=" + estadoTimbre +
                ", metodoPago=" + metodoPago +
                ", esEstadoError=" + esEstadoError +
                ", fechaContrato=" + fechaContrato +
                ", personaId=" + personaId +
                ", usuarioId=" + usuarioId +
                ", grupoNomina=" + grupoNomina +
                ", fecIniPeriodo=" + fecIniPeriodo +
                ", fecFinPeriodo=" + fecFinPeriodo +
                ", fecIniIncidencia=" + fecIniIncidencia +
                ", fecFinIncidencia=" + fecFinIncidencia +
                ", estatusPago=" + estatusPago +
                '}';
    }
}
