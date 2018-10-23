/* 
 * @(#)DriverStatusEnum.java    Created on 2014-08-13
 * Copyright (c) 2014 HZBenhe, Inc. All rights reserved.
 * $Id$
 */
package com.jopool.chargingStation.www.enums;

/**
 * @Package Name : com.jopool.chargingStation.www.enums
 * @Author : soupcat
 * @Creation Date : 2017/8/28 下午10:43
 */
public enum SmsTypeEnum {

    LOGIN("LOGIN", 1, "登录"), REGISTER("REGISTER", 2, "注册"), CHANGE_PASSWORD("CHANGE_PASSWORD", 3, "修改密码"),CHANGE_PHONE("CHANGE_PHONE",4,"修改手机号");

    private String key;
    private int    value;
    private String name;

    SmsTypeEnum(String key, int value, String name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.name();
    }

    public static SmsTypeEnum valueOf(int value) {
        SmsTypeEnum result = LOGIN;
        for (SmsTypeEnum e : SmsTypeEnum.values()) {
            if (e.getValue() == value) {
                result = e;
            }
        }
        return result;
    }

}
