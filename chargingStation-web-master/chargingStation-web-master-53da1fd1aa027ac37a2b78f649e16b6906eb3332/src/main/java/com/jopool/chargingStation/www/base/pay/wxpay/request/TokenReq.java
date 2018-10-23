package com.jopool.chargingStation.www.base.pay.wxpay.request;

/**
 * Created by gexin on 16/4/13.
 */
public class TokenReq {
    private String grant_type;//获取access_token填写client_credential
    private String appid;//第三方用户唯一凭证
    private String secret;//第三方用户唯一凭证密钥，即appsecret

    public TokenReq(String appid, String secret) {
        this.grant_type = "client_credential";
        this.appid = appid;
        this.secret = secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
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
}
