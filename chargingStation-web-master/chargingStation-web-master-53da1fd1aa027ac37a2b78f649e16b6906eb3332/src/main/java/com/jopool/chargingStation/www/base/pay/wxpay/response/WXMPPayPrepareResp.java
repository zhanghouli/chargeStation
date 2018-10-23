package com.jopool.chargingStation.www.base.pay.wxpay.response;

import com.jopool.chargingStation.www.base.pay.wxpay.common.Signature;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.BaseReqData;
import com.jopool.jweb.utils.MathUtils;
import com.jopool.jweb.utils.UUIDUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号支付需要的config参数
 * <p/>
 * Created by gexin on 16/1/12.
 */
public class WXMPPayPrepareResp extends BaseReqData {
    private String appId;//  必填，公众号的唯一标识
    private String timestamp;// 必填，生成签名的时间戳
    private String nonceStr; // 必填，生成签名的随机串
    private String signature;// 必填，签名，见附录1''

    public WXMPPayPrepareResp(String appid, String url, String jsapi_ticket) {
        this.appId = appid;
        this.nonceStr = UUIDUtils.createId();
        DecimalFormat format = new DecimalFormat("#");
        this.timestamp = format.format(MathUtils.div(new Date().getTime(), 1e3, 0));
        //根据API给的签名规则进行签名
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("noncestr", this.nonceStr);
        map.put("jsapi_ticket", jsapi_ticket);
        map.put("timestamp", this.timestamp);
        map.put("url", url);
        String sign = Signature.getSHA1Sign(map);
        this.signature = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
