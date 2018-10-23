package com.jopool.chargingStation.www.response.common;

import java.io.Serializable;

/**
 * @Package Name : com.jopool.chargingStation.www.response.common
 * @Author : soupcat
 * @Creation Date : 2017/8/29 下午5:58
 */
public class LoginResp implements Serializable {
    private String phone;
    private String token;
    private String passportId;
    private String userId;
    private String userType;
    private String ext;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
