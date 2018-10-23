package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/29.
 */
public enum StationWarningEnum {
    TEMPERATURE_OVERRUN(1, "温度超限"), CHASSIS_OPENED(2, "机箱门被打开"), POWER_FAILURE(3, "停电"), SMOKE_WARNING(4, "烟感");

    private int    value;
    private String name;

    private StationWarningEnum(int value, String name) {
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
