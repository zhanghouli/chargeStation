package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/30.
 */
public enum DeviceStatusEnum {
    ONLINE("online", "在线"), OFFLINE("offline", "离线");

    private String value;

    private String name;


    private DeviceStatusEnum(String value, String name) {
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
