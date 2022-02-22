package com.aura.nomina.parametryx;

import java.util.List;

public class StoreProcedure {
    private String procedureName;
    private boolean array;
    private Integer inputFieldsAmount;

    private List<Field> fields;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public Integer getInputFieldsAmount() {
        return inputFieldsAmount;
    }

    public void setInputFieldsAmount(Integer inputFieldsAmount) {
        this.inputFieldsAmount = inputFieldsAmount;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "StoreProcedure{" +
                "procedureName='" + procedureName + '\'' +
                ", inputFieldsAmount=" + inputFieldsAmount +
                ", fields=" + fields +
                '}';
    }
}
