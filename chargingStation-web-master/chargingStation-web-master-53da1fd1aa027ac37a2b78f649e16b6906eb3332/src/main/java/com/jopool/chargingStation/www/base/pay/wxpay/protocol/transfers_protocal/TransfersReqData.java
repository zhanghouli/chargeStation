package com.jopool.chargingStation.www.base.pay.wxpay.protocol.transfers_protocal;

import com.jopool.chargingStation.www.base.pay.wxpay.common.Signature;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.BaseReqData;
import com.jopool.jweb.utils.UUIDUtils;

/**
 * 请求统一下单API需要提交的数据
 * Created by gexin on 16/1/12.
 */
public class TransfersReqData extends BaseReqData {
    //每个字段具体的意思请查看API文档
    private String mch_appid        = "";//微信分配的公众账号ID（企业号corpid即为此appId）
    private String mchid            = "";//微信支付分配的商户号
    private String device_info      = "";//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    private String nonce_str        = "";//随机字符串，不长于32位
    private String sign             = "";//签名
    private String partner_trade_no = "";//商户订单号，需保持唯一性(只能是字母或者数字，不能包含有符号)
    private String openid           = "";//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
    private String check_name       = "";//NO_CHECK：不校验真实姓名;FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
    private String re_user_name     = "";//收款用户真实姓名。如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
    private int    amount           = 0;//企业付款金额，单位为分
    private String desc             = "";//企业付款操作说明信息。必填。
    private String spbill_create_ip = "";//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。

    public TransfersReqData(WXAbstractConfig wxConfig, String deviceInfo, String partner_trade_no, int amount, String openid, String desc) {
        this.mch_appid = wxConfig.getAppId();
        this.mchid = wxConfig.getMchId();
        this.device_info = deviceInfo;
        this.nonce_str = UUIDUtils.createId();
        this.partner_trade_no = partner_trade_no;
        this.openid = openid;
        this.check_name = "NO_CHECK";
        this.re_user_name = "";
        this.amount = amount;
        this.desc = desc;
        this.spbill_create_ip = wxConfig.getIp();
        //根据API给的签名规则进行签名
        String sign = Signature.getSign(wxConfig, toMap());
        this.sign = sign;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
}
