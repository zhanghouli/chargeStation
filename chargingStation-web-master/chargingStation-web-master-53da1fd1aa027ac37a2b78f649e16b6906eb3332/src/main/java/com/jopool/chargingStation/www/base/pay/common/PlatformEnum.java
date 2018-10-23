package com.jopool.chargingStation.www.base.pay.common;

/**
 * 支付时,上传的platform字段的类型
 * <p>
 * Created by gexin on 16/3/27.
 */
public enum PlatformEnum {
    APP("APP", "APP"), WEB("WEB", "WEB"), GZH("GZH", "GZH"), WXXCX("WXXCX", "WXXCX");

    private String value;
    private String name;

    PlatformEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static PlatformEnum valueOfStr(String value) {
        PlatformEnum result = APP;
        for (PlatformEnum e : PlatformEnum.values()) {
            if (e.getValue().equals(value)) {
                result = e;
            }
        }
        return result;
    }
}
