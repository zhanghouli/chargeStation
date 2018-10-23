package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/30.
 */
public enum OrderStatusEnum {
    WAITING_FOR_PAY("waiting_for_pay", "待支付"), WAITING_FOR_CONNECT("waiting_for_connect", "待连接插口"), RECHARGING("recharging", "充电中"), FINISHED("finished", "充电完成"), CLOSE("close", "订单关闭");

    private String value;

    private String name;


    private OrderStatusEnum(String value, String name) {
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


    public static OrderStatusEnum getVualeof(String value) {
        OrderStatusEnum result = WAITING_FOR_PAY;
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.getValue().equals(value)) {
                result = e;
            }
        }
        return result;
    }
}
