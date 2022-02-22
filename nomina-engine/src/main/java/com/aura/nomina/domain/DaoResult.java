package com.aura.nomina.domain;

public class DaoResult {
    private String result;
    private Integer errCode;
    private String errDescription;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrDescription() {
        return errDescription;
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }

    public DaoResult(String result, Integer errCode, String errDescription) {
        this.result = result;
        this.errCode = errCode;
        this.errDescription = errDescription;
    }

    public DaoResult() {
    }
}
