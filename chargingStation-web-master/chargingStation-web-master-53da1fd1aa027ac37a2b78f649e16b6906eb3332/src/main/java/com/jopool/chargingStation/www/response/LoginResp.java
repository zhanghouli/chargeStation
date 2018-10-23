package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Passport;

/**
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017/9/1 下午4:09
 */
public class LoginResp {
    private String userName;
    private String userId;
    private String token;
    private String realName;
    //
    private String carOwnerId;

    public LoginResp() {
    }

    public LoginResp(Passport passport, String token) {
        this.userName = passport.getUserName();
        this.userId = passport.getId();
        this.token = token;
        this.realName = passport.getRealName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
    }
}
