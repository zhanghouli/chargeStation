package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/30.
 */
public enum StationPortStatusEnum {
    FREE("free", "空闲"), WORKING("working", "使用"), DISABLE("disable", "禁用"), FAULT("fault", "故障"), SUSPEND("suspend", "暂停");

    private String value;

    private String name;


    private StationPortStatusEnum(String value, String name) {
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
