package com.aura.nomina.domain;

public class Test {
    private String id;
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
