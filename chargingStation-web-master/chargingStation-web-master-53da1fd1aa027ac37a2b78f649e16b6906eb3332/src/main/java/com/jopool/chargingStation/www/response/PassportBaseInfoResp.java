package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Passport;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年08月25日 下午1:55
 */
public class PassportBaseInfoResp {
    private String id;
    private String avatar;
    private String realName;
    private String userName;
    private String phone;
    private int    amount;
    private int    historyRestTime;
    private String deviceNumber;

    public PassportBaseInfoResp(Passport passport) {
        this.id = passport.getId();
        this.realName = passport.getRealName();
        this.userName = passport.getUserName();
        this.phone = passport.getPhone();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getHistoryRestTime() {
        return historyRestTime;
    }

    public void setHistoryRestTime(int historyRestTime) {
        this.historyRestTime = historyRestTime;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
