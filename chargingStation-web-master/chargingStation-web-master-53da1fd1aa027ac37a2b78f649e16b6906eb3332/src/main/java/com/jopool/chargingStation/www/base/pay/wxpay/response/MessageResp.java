package com.jopool.chargingStation.www.base.pay.wxpay.response;

/**
 * Created by gexin on 2017/6/28.
 */
public class MessageResp {
    private int    errcode;
    private String errmsg;
    private String msgid;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
