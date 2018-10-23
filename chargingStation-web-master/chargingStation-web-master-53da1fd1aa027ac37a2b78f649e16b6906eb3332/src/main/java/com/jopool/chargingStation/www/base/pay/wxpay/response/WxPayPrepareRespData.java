package com.jopool.chargingStation.www.base.pay.wxpay.response;

import com.jopool.chargingStation.www.base.pay.common.PlatformEnum;
import com.jopool.chargingStation.www.base.pay.wxpay.common.Signature;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.BaseReqData;
import com.jopool.jweb.utils.MathUtils;
import com.jopool.jweb.utils.UUIDUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付统一下单响应
 * <p>
 * Created by gexin on 16/1/12.
 */
public class WxPayPrepareRespData extends BaseReqData {
    private String tradeNo;
    //每个字段具体的意思请查看API文档
    private String appid        = "";//微信分配的公众账号ID（企业号corpid即为此appId）
    private String partnerid    = "";//微信支付分配的商户号
    private String prepayid     = "";//微信返回的支付交易会话ID
    private String packageValue = "";//暂填写固定值Sign=WXPay
    private String noncestr     = "";//随机字符串，不长于32位
    private String timestamp    = "";//时间戳
    private String sign         = "";//签名

    public WxPayPrepareRespData(WXAbstractConfig wxConfig, String platform, String prepayid, String tradeNo) {
        if (PlatformEnum.APP.getValue().equals(platform)) {
            this.tradeNo = tradeNo;
            this.appid = wxConfig.getAppId();
            this.partnerid = wxConfig.getMchId();
            this.prepayid = prepayid;
            this.packageValue = "Sign=WXPay";
            this.noncestr = UUIDUtils.createId();
            DecimalFormat format = new DecimalFormat("#");
            this.timestamp = format.format(MathUtils.div(new Date().getTime(), 1e3, 0));
            //根据API给的签名规则进行签名
            Map<String, Object> map = toMap();
            map.remove("tradeNo");
            map.remove("packageValue");
            map.put("package", this.packageValue);
            String sign = Signature.getSign(wxConfig, map);
            this.sign = sign;
        } else {
            this.tradeNo = tradeNo;
            this.appid = wxConfig.getAppId();
            this.partnerid = wxConfig.getMchId();
            this.prepayid = prepayid;
            this.packageValue = "prepay_id=" + prepayid;
            this.noncestr = UUIDUtils.createId();
            DecimalFormat format = new DecimalFormat("#");
            this.timestamp = format.format(MathUtils.div(new Date().getTime(), 1e3, 0));
            //根据API给的签名规则进行签名
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("appId", this.appid);
            map.put("timeStamp", this.timestamp);
            map.put("nonceStr", this.noncestr);
            map.put("package", this.packageValue);
            map.put("signType", "MD5");
            String sign = Signature.getSign(wxConfig, map);
            this.sign = sign;
        }
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage() {
        return packageValue;
    }

    public void setPackage(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
