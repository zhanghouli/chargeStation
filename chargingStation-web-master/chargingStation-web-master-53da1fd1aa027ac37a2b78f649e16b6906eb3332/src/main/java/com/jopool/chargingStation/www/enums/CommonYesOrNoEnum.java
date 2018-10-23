package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/29.
 */
public enum CommonYesOrNoEnum {
    ALL(-1,"全部"),YES(1,"正常"),NO(0,"禁用");

    private int value;
    private String name;

    private CommonYesOrNoEnum(int value,String name){
        this.value=value;
        this.name=name;
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
