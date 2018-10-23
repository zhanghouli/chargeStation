package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/30.
 */
public enum FenceStatusEnum {
    IN("in", "在区域内"), OUT("out", "在区域外"), UNKNOW("unknow", "未知");

    private String value;

    private String name;


    private FenceStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
