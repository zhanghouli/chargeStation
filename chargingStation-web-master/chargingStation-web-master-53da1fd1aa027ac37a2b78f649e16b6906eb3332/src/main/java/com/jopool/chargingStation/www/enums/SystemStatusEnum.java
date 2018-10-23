package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/12/18.
 */
public enum SystemStatusEnum {
    IOS("IOS", "苹果系统"), ANDROID("ANDROID", "安卓系统");

    private String name;
    private String value;

    private SystemStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
