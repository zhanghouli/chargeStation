package com.jopool.chargingStation.www.enums;

/**
 * @Package Name : com.jopool.chargingStation.www.enums
 * @Author : soupcat
 * @Creation Date : 2017/9/7 下午9:29
 */
public enum RechargeOrderStatusEnum {
    WAITING_FOR_PAY("waiting_for_pay", "待支付"), PAYED("payed", "已支付");

    private String value;

    private String name;


    private RechargeOrderStatusEnum(String value, String name) {
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
