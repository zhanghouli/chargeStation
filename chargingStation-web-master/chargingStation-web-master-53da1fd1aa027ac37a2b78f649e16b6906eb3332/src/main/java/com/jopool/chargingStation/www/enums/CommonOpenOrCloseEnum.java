package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/29.
 */
public enum CommonOpenOrCloseEnum {
    OPEN(1,"开启"),CLOSE(0,"关闭");

    private int value;
    private String name;

    private CommonOpenOrCloseEnum(int value,String name){
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
