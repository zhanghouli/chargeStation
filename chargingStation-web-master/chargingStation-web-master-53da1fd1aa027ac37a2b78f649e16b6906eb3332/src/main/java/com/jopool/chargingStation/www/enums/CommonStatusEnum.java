package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/27.
 */
public enum CommonStatusEnum {
    ALL(-1, "全部"), NORMAL(1, "正常"), DISABLE(0, "禁用");

    private int    value;
    private String name;

    private CommonStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
