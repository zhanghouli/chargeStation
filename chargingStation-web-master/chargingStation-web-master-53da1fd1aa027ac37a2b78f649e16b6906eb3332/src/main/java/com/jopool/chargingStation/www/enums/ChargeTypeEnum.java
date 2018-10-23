package com.jopool.chargingStation.www.enums;

/**
 * @Package Name : com.jopool.chargingStation.www.enums
 * @Author : soupcat
 * @Creation Date : 2017/11/17 下午2:23
 * time-按时间套餐 electricity-按电量电功率充值
 */
public enum ChargeTypeEnum {
    TIME("time", "时间套餐"), ELECTRICITY("electricity", "按电量电功率充值");

    private String value;

    private String name;


    private ChargeTypeEnum(String value, String name) {
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
