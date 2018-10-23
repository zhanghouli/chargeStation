package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/24.
 */
public enum PassportAccountLogTypeEnum {
    ALL("", "全部"), RECHARGE("recharge", "充值"), RETURN("return", "退款"), PAY("pay", "消费"), WITHDRAWALS("withdrawals", "提现"), INCOME("income", "收入");

    private String value;
    private String name;

    private PassportAccountLogTypeEnum(String value, String name) {
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
