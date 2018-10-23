package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/11/27.
 */
public enum MessageTemplateEnum {
    ALL("", "全部"), RECHARGE_AMOUNT("recharge_amount", "金额充值提示通知"), START_CHARGE("start_charge", "金额充电开始通知"), CHARGE_COMPLETE("charge_complete", "金额充电结束通知"), TIME_CHARGE("time_charge", "时间余量充电通知"), TIME_CHARGE_COMPLETE("time_charge_complete", "时间余量充电结束通知");


    private String value;
    private String name;

    private MessageTemplateEnum(String value, String name) {
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


    public static String getEnumName(String value) {
        MessageTemplateEnum messageTemplateEnum = START_CHARGE;
        for (MessageTemplateEnum em : MessageTemplateEnum.values()) {
            if (em.getValue().equals(value)) {
                messageTemplateEnum = em;
                break;
            }
        }
        return messageTemplateEnum.getName();
    }
}
