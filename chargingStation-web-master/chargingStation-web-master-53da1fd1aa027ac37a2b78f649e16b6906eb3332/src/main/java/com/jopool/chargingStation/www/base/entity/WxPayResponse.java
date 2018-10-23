package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.base.pay.wxpay.protocol.common_protocol.NotifyResData;
import com.jopool.jweb.utils.UUIDUtils;
import weixin.popular.bean.paymch.MchPayNotify;

import java.util.Date;

public class WxPayResponse {
    private String id;

    private String appid;

    private String mchId;

    private String deviceInfo;

    private String nonceStr;

    private String sign;

    private String resultCode;

    private String errCode;

    private String errCodeDes;

    private String openid;

    private String isSubscribe;

    private String tradeType;

    private String bankType;

    private String totalFee;

    private String feeType;

    private String cashFee;

    private String cashFeeType;

    private String couponFee;

    private String couponCount;

    private String couponIdN;

    private String couponFeeN;

    private String transactionId;

    private String outTradeNo;

    private String attach;

    private String timeEnd;

    private Date creationTime;

    public WxPayResponse(NotifyResData notifyResData) {
        this.id = UUIDUtils.createId();
        this.appid = notifyResData.getAppid();
        this.mchId = notifyResData.getMch_id();
        this.deviceInfo = notifyResData.getDevice_info();
        this.nonceStr = notifyResData.getNonce_str();
        this.sign = notifyResData.getSign();
        this.resultCode = notifyResData.getResult_code();
        this.errCode = notifyResData.getErr_code();
        this.errCodeDes = notifyResData.getErr_code_des();
        this.openid = notifyResData.getOpenid();
        this.isSubscribe = notifyResData.getIs_subscribe();
        this.tradeType = notifyResData.getTrade_type();
        this.bankType = notifyResData.getBank_type();
        this.totalFee = notifyResData.getTotal_fee();
        this.feeType = notifyResData.getFee_type();
        this.cashFee = notifyResData.getCash_fee();
        this.cashFeeType = notifyResData.getCash_fee_type();
        this.couponFee = notifyResData.getCoupon_fee();
        this.couponCount = notifyResData.getCoupon_count();
        this.couponIdN = notifyResData.getCoupon_id_$n();
        this.couponFeeN = notifyResData.getCoupon_fee_$n();
        this.transactionId = notifyResData.getTransaction_id();
        this.outTradeNo = notifyResData.getOut_trade_no();
        this.attach = notifyResData.getAttach();
        this.timeEnd = notifyResData.getTime_end();
        this.creationTime = new Date();
    }

    public WxPayResponse(MchPayNotify notifyResData) {
        this.id = UUIDUtils.createId();
        this.appid = notifyResData.getAppid();
        this.mchId = notifyResData.getMch_id();
        this.deviceInfo = notifyResData.getDevice_info();
        this.nonceStr = notifyResData.getNonce_str();
        this.sign = notifyResData.getSign();
        this.resultCode = notifyResData.getResult_code();
        this.errCode = notifyResData.getErr_code();
        this.errCodeDes = notifyResData.getErr_code_des();
        this.openid = notifyResData.getOpenid();
        this.isSubscribe = notifyResData.getIs_subscribe();
        this.tradeType = notifyResData.getTrade_type();
        this.bankType = notifyResData.getBank_type();
        this.totalFee = notifyResData.getTotal_fee() + "";
        this.feeType = notifyResData.getFee_type();
        this.cashFee = notifyResData.getCash_fee() + "";
        this.cashFeeType = notifyResData.getCash_fee_type();
        this.couponFee = notifyResData.getCoupon_fee() + "";
        this.couponCount = notifyResData.getCoupon_count() + "";
        this.transactionId = notifyResData.getTransaction_id();
        this.outTradeNo = notifyResData.getOut_trade_no();
        this.attach = notifyResData.getAttach();
        this.timeEnd = notifyResData.getTime_end();
        this.creationTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponIdN() {
        return couponIdN;
    }

    public void setCouponIdN(String couponIdN) {
        this.couponIdN = couponIdN;
    }

    public String getCouponFeeN() {
        return couponFeeN;
    }

    public void setCouponFeeN(String couponFeeN) {
        this.couponFeeN = couponFeeN;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}