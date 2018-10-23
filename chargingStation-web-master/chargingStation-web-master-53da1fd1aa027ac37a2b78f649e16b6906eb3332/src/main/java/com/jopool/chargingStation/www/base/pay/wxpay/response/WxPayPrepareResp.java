package com.jopool.chargingStation.www.base.pay.wxpay.response;

import com.alibaba.fastjson.JSON;
import weixin.popular.bean.paymch.MchPayApp;

import java.util.Map;

/**
 * Created by xuan on 16/6/14.
 */
public class WxPayPrepareResp {
    private String    outTradeNo;
    private String    codeUrl;
    private JSON      mpRequestData;
    private MchPayApp appRequestData;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public JSON getMpRequestData() {
        return mpRequestData;
    }

    public void setMpRequestData(JSON mpRequestData) {
        this.mpRequestData = mpRequestData;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public MchPayApp getAppRequestData() {
        return appRequestData;
    }

    public void setAppRequestData(MchPayApp appRequestData) {
        this.appRequestData = appRequestData;
    }
}
