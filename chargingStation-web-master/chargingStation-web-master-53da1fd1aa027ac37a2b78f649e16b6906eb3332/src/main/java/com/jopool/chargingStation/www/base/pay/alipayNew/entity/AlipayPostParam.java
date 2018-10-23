package com.jopool.chargingStation.www.base.pay.alipayNew.entity;


import com.jopool.chargingStation.www.base.pay.alipayNew.config.AlipayConfig;
import com.jopool.jweb.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package Name : com.jopool.chargingStation.www.base.pay.alipayNew.entity
 * @Author : soupcat
 * @Creation Date : 2017/11/28 下午3:53
 */
public class AlipayPostParam {
    private String app_id;//支付宝分配给开发者的应用ID
    private String method;//接口名称
    private String format;//仅支持JSON
    private String charset;//charset
    private String sign_type;//sign_type
    private String sign;//商户请求参数的签名串，详见
    private String timestamp;//发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    private String version = "1.0";//调用的接口版本，固定为：1.0
    private String notify_url;//支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
    private String biz_content;
    //
    private String body;//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
    private String subject;//商品的标题/交易标题/订单标题/订单关键字等。
    private String out_trade_no;//商户网站唯一订单号
    private String timeout_express;//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天 注：若为空，则默认为15d
    private String total_amount;
    private String product_code;//销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY


    /**
     * 构造
     *
     * @param out_trade_no
     * @param subject
     * @param total_amount
     * @param body
     * @param notify_url
     * @param return_url
     */
    public AlipayPostParam(String out_trade_no, String subject, String total_amount, String body, String notify_url, String return_url) {
        this.method = "alipay.trade.app.pay";
        this.app_id = AlipayConfig.APPID;
        this.charset = AlipayConfig.CHARSET;
        this.sign_type = AlipayConfig.SIGNTYPE;
        this.format = AlipayConfig.FORMAT;
        this.notify_url = AlipayConfig.notify_url;
        this.timestamp = DateUtils.date2StringBySecond(new Date());
        this.body = body;
        this.subject = subject;
        this.out_trade_no = out_trade_no;
        this.total_amount = total_amount;
        this.product_code = "QUICK_MSECURITY_PAY";
    }


    /**
     * 封装成map
     *
     * @return
     */
    public Map<String, String> toMap() {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("method", method);
        sParaTemp.put("app_id", app_id);
        sParaTemp.put("charset", charset);
        sParaTemp.put("sign_type", sign_type);
        sParaTemp.put("format", format);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("body", body);
        sParaTemp.put("timestamp", timestamp);
        sParaTemp.put("subject", subject);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("total_amount", total_amount);
        sParaTemp.put("product_code", product_code);
        return sParaTemp;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
