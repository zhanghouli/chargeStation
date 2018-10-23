package com.jopool.chargingStation.www.enums;

/**
 * @Package Name : com.jopool.chargingStation.www.enums
 * @Author : soupcat
 * @Creation Date : 2017/9/7 下午9:28
 */
public enum RechargeOrderTypeEnum {
    ORDER("order", "直接充电"), RECHARGE_PACKAGE("recharge_package", "充值套餐");

    private String value;

    private String name;


    private RechargeOrderTypeEnum(String value, String name) {
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
