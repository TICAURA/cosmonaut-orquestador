package com.aura.nomina.domain;

import java.time.LocalDate;

public class Dummy {
    private Integer cliente_id;
    private LocalDate fecha_contrato;
    private Integer persona_id;
    private Integer politica_id;
    private Integer grupo_nomina;
    private Integer tipo_compensacion;
    private Integer sbm_imss;
    private LocalDate fec_ini_periodo;

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
}
