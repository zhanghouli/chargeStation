package com.jopool.chargingStation.www.base.pay.wxpay.protocol;

/**
 * Created by gexin on 16/1/12.
 */
public class BaseResData {

    //协议层
    protected String return_code = "";
    protected String return_msg  = "";

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
}
