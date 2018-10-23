package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/24.
 */
public enum PassportStatusEnum {
    ALL("all", "全部"), NORMAL("normal", "正常"), DISABLE("disable", "禁用");

    private String value;
    private String name;

    private PassportStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.name();
    }
}
