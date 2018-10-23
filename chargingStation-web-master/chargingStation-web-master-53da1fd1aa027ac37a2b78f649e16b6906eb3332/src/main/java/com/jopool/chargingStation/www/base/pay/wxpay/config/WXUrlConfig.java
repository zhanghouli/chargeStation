package com.jopool.chargingStation.www.base.pay.wxpay.config;

/**
 * 微信的调用地址
 * <p>
 * Created by xuan on 16/12/29.
 */
public class WXUrlConfig {
    /**
     * 微信公众号,去授权地址
     */
    public static final String WX_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 获取access token地址
     */
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取微信用户信息地址
     */
    public static final String WX_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 获取token地址
     */
    public static final String WX_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 获取ticket地址
     */
    public static final String WX_GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 企业付款
     */
    public static final String WX_TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     * 模板消息
     */
    public static final String WX_MESSAGE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 获取媒体资源
     */
    public static final String WX_GET_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
}
