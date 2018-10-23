package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/29.
 */
public enum StationStatusEnum {
    NORMAL("normal", "正常"), DISABLE("disable", "禁用"), FAULT("fault", "故障");

    private String value;
    private String name;

    private StationStatusEnum(String value, String name) {
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
