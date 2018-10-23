package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/8/31.
 */

/**
 * 反馈建  type  类型
 */
public enum FeedBackTypeEnum {
    ALL("","全部"),OPERATOR("operator","运营商"),CAROWNER("carowner","车主"),ESTATE("estate", "物业"),GOVERNMENT("government","政府");

    private String value;

    private String name;

    FeedBackTypeEnum(String value,String name){
        this.value=value;
        this.name=name;
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
