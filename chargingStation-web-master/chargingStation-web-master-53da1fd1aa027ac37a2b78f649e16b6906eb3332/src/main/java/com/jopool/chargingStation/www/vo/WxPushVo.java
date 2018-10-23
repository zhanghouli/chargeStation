package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.MessageTemplate;
import com.jopool.chargingStation.www.base.entity.PassportAccount;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;

/**
 * Created by synn on 2017/11/27.
 */
public class WxPushVo {
    //推送 凭证 条件
    private TokenResp       tokenResp;
    //模版ID
    private MessageTemplate messageTemplate;
    //车主
    private Carowner        carowner;
    //退款 || 充值金额
    private String          money;
    //充值类型
    private String          moneyType;
    //剩余 时间
    private String          time;

    private PassportAccount passportAccount;

    public WxPushVo() {

    }

    public WxPushVo(TokenResp tokenResp, MessageTemplate messageTemplate) {
        this.tokenResp = tokenResp;
        this.messageTemplate = messageTemplate;
    }

    public WxPushVo(TokenResp tokenResp, MessageTemplate messageTemplate, Carowner carowner) {
        this.tokenResp = tokenResp;
        this.messageTemplate = messageTemplate;
        this.carowner = carowner;
    }

    public WxPushVo(TokenResp tokenResp, MessageTemplate messageTemplate, Carowner carowner, PassportAccount passportAccount) {
        this.tokenResp = tokenResp;
        this.messageTemplate = messageTemplate;
        this.carowner = carowner;
        this.passportAccount = passportAccount;
    }

    //剩余 时间
    public WxPushVo(TokenResp tokenResp, MessageTemplate messageTemplate, Carowner carowner, String time) {
        this.tokenResp = tokenResp;
        this.messageTemplate = messageTemplate;
        this.carowner = carowner;
        this.time = time;
    }

    //关于金额
    public WxPushVo(TokenResp tokenResp, MessageTemplate messageTemplate, Carowner carowner, String money, String moneyType) {
        this.tokenResp = tokenResp;
        this.messageTemplate = messageTemplate;
        this.carowner = carowner;
        this.money = money;
        this.moneyType = moneyType;
    }

    public TokenResp getTokenResp() {
        return tokenResp;
    }

    public void setTokenResp(TokenResp tokenResp) {
        this.tokenResp = tokenResp;
    }

    public MessageTemplate getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(MessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Carowner getCarowner() {
        return carowner;
    }

    public void setCarowner(Carowner carowner) {
        this.carowner = carowner;
    }

    public PassportAccount getPassportAccount() {
        return passportAccount;
    }

    public void setPassportAccount(PassportAccount passportAccount) {
        this.passportAccount = passportAccount;
    }
}
