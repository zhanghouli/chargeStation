package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/24.
 */
public enum PassportTypeEnum {
    ADMIN("admin", "管理员"),GOVERNMENT("government","政府"), OPERATOR("operator", "运营商"), ESTATE("estate", "物业"),CAROWNER("carowner","车主");

    private String value;

    private String name;

    private PassportTypeEnum(String value, String name) {
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


    public static String getEnumName(String value){
        PassportTypeEnum passportTypeEnum = OPERATOR;
        for (PassportTypeEnum em : PassportTypeEnum.values()) {
            if (em.getValue().equals(value)) {
                passportTypeEnum = em;
                break;
            }
        }
        return passportTypeEnum.getName();
    }
}
