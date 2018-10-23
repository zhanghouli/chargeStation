package com.jopool.chargingStation.www.base.pay.wxpay.request;


import com.jopool.chargingStation.www.base.entity.WxConfig;

import java.io.Serializable;

/**
 * Created by gexin on 16/4/12.
 */
public class AccessTokenReq implements Serializable {
    private String appid;//公众号的唯一标识
    private String secret;//公众号的appsecret
    private String code;//填写第一步获取的code参数
    private String grant_type;//填写为authorization_code

    public AccessTokenReq(String code, WxConfig wxConfig) {
        this.appid = wxConfig.getPayAppid();
        this.secret = wxConfig.getPaySecret();
        this.code = code;
        this.grant_type = "authorization_code";
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
