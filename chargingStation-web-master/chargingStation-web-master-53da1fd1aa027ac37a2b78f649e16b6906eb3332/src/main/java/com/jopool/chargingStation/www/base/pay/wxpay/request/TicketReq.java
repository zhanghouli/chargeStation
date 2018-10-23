package com.jopool.chargingStation.www.base.pay.wxpay.request;

/**
 * Created by gexin on 16/4/13.
 */
public class TicketReq {
    private String access_token;
    private String type;

    public TicketReq(String access_token) {
        this.access_token = access_token;
        this.type="jsapi";
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
