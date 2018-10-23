package com.jopool.chargingStation.www.base.pay.wxpay.request;


import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;

/**
 * Created by gexin on 16/4/12.
 */
public class AuthorizeReq {
    String appid;//公众号的唯一标识
    String redirect_uri;//授权后重定向的回调链接地址，请使用urlencode对链接进行处理
    String response_type;//返回类型，请填写code
    String scope;//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
    String state;//重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节

    public AuthorizeReq(boolean isUserInfo, String state, WxConfig payWx) {
        this.appid = payWx.getPayAppid();
        this.redirect_uri = ApplicationConfigHelper.getBaseUrl() + "/api/common/wx/oauth2.htm";
        this.response_type = "code";
        this.scope = isUserInfo ? "snsapi_userinfo" : "snsapi_base";
        this.state = state;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
