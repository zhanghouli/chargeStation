package com.jopool.chargingStation.www.vo;

import com.jopool.jweb.entity.BaseSessionUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gexin on 15/12/15.
 */
public class SessionUser  extends BaseSessionUser {
    /**
     * 登录后所拥有的权限
     */
    private String name;

    private String passportType;

    private String userId;

    private String passportId;

    Map<PassportAuthVo, List<PassportAuthVo>> auth = new HashMap<PassportAuthVo, List<PassportAuthVo>>();

    public Map<PassportAuthVo, List<PassportAuthVo>> getAuth() {
        return auth;
    }

    public void setAuth(Map<PassportAuthVo, List<PassportAuthVo>> auth) {
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }
}
