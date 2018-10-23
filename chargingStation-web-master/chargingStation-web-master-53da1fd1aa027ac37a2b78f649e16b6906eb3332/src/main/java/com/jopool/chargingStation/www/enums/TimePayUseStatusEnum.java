package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/24.
 */
public enum TimePayUseStatusEnum {
    USERING("usering", "全部"), NOT_USED("not_used", "未使用");

    private String value;
    private String name;

    private TimePayUseStatusEnum(String value, String name) {
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
