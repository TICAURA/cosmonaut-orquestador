package com.aura.nomina.parametryx;

public class Field {
    private String name;
    private String type;
    private Integer index;
    private String mapedValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMapedValue() {
        return mapedValue;
    }

    public void setMapedValue(String mapedValue) {
        this.mapedValue = mapedValue;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", mapedValue='" + mapedValue + '\'' +
                '}';
    }
}
