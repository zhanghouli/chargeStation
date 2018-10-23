package com.jopool.chargingStation.www.enums;

/**
 * Created by synn on 2017/9/4.
 */
public enum CommonPictureTypeEnum {
    AVATAR("avatar", "用户头像"), FEEDBACKPIC("feedbackPic", "反馈图片");

    private String value;
    private String name;

    CommonPictureTypeEnum(String value, String name) {
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
