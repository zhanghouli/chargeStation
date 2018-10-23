package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/31.
 */
public enum PlatformEnum {
    GZH("GZH", "GZH"), WEB("WEB", "WEB"), WXXCX("WXXCX", "WXXCX"),APP("APP","APP");
    private String value;
    private String name;

    PlatformEnum(String value, String name) {
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
