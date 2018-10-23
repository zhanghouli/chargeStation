package com.jopool.chargingStation.www.base.pay.wxpay.config;

import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;

/**
 * Created by gexin on 16/1/14.
 */
public class WXAbstractConfig {
    //以下是几个API的路径：
    //0）统一下单API
    public static final String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //1）被扫支付API
    public static final String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    //2）被扫支付查询API
    public static final String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

    //3）退款API
    public static final String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //4）退款查询API
    public static final String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

    //5）撤销API
    public static final String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    //6）下载对账单API
    public static final String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

    //7) 统计上报API
    public static final String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

    //8) 统计上报API
    public static final String TRANSFERS_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public static final String HttpsRequestClassName = "com.jopool.chargingStation.www.base.pay.wxpay.common.HttpsRequest";

    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
    private String key;

    //微信分配的公众号ID（开通公众号之后可以获取到）
    private String appId;

    //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private String mchId;

    //HTTPS证书的本地路径
    private String certLocalPath;

    //HTTPS证书密码，默认密码等于商户号MCHID
    private String certPassword;

    //异步回调地址
    private String notifyUrl;

    //受理模式下给子商户分配的子商户号
    private String submchId = "";

    //机器IP
    private String ip = "";

    //是否使用异步线程的方式来上报API测速，默认为异步模式
    private boolean usethreadtodoreport = true;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getCertLocalPath() {
        return certLocalPath;
    }

    public void setCertLocalPath(String certLocalPath) {
        this.certLocalPath = certLocalPath;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSubmchId() {
        return submchId;
    }

    public void setSubmchId(String submchId) {
        this.submchId = submchId;
    }

    public boolean isUsethreadtodoreport() {
        return usethreadtodoreport;
    }

    public void setUsethreadtodoreport(boolean usethreadtodoreport) {
        this.usethreadtodoreport = usethreadtodoreport;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static WXAbstractConfig obtain(WxConfig payWx) {
        WXAbstractConfig config = new WXAbstractConfig();
        config.setKey(payWx.getPayKey());
        config.setAppId(payWx.getPayAppid());
        config.setMchId(payWx.getMchId());
        config.setCertLocalPath(ApplicationConfigHelper.getFilePath() + payWx.getCert());
        config.setCertPassword(payWx.getCertPassword());
        config.setNotifyUrl(payWx.getNotifyUrl());
        config.setSubmchId(payWx.getSubMchId());
        config.setIp(payWx.getIp());
        return config;
    }

}
