package com.jopool.chargingStation.www.base.pay.wxpay.request;

/**
 * Created by gexin on 16/4/12.
 */
public class UserInfoReq {
    private String access_token;//网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
    private String openid;//用户的唯一标识
    private String lang;//返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语

    public UserInfoReq(String access_token, String openid) {
        this.access_token = access_token;
        this.openid = openid;
        this.lang = "zh_CN";
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
