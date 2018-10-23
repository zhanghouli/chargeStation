package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/30.
 */
public enum PayTypeEnum {
    WECHAT("wechat", "微信支付"), BALANCE("balance", "账户支付"), ALIPAY("alipay", "支付宝支付"), TIMEPAY("timepay", "历史剩余时间支付");

    private String value;

    private String name;

    private PayTypeEnum(String value, String name) {
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
