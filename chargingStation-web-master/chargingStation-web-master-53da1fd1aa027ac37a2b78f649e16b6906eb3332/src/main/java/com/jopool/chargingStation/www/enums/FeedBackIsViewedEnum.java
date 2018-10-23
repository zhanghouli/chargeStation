package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/31.
 */
public enum FeedBackIsViewedEnum {
    ALL(-1,"全部"),READ(1,"已阅"),NOTREAD(0,"未阅");
    private int value;
    private String name;

    FeedBackIsViewedEnum(int value,String name){
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
