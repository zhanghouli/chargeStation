/* 
 * @(#)Constants.java    Created on 2013-11-8
 * Copyright (c) 2013 HzBenhe, Inc. All rights reserved.
 * $Id$
 */
package com.jopool.chargingStation.www.constants;

/**
 * 常量
 *
 * @author wangyl
 * @version $Revision: 1.0 $, $Date: 2014-3-14 下午2:27:05 $
 */
public class Constants {
    //id
    public static final String SYSTEM_ID = "00000000000000000000000000000000";

    //
    public static final String DEFAULT_PWD                                   = "12345"; //默认密码
    public static final String NO_VERIFICATION_PHONE                         = "15868474170";//
    public static final String DEFAULT_PROPERTY_ID                           = "00000000000000000000000000000000"; //默认产品属性ID
    public static final String DEFAULT_PROPERTY_NAME                         = "规格"; //默认产品属性
    public static final String DEFAULT_PROPERTY_VAL_NAME                     = "默认"; //默认产品属性值
    public static final String NUMBER_PREFIX_PASSPORT                        = "PP";// number pre
    public static final long   ACCESS_TOKEN_CACHE_TIME                       = 100; //token缓存时间，单位分钟
    //redis
    public static final String REDIS_KEY_LBS_RECT                            = "redis_key_lbs_rect_";
    public static final int    NERAYBY_SHOP_COUNT                            = 5;
    //
    public static final String IMG_FIX_SCRIPT                                = "<script>var imgs = document.getElementsByTagName('img');for(var i in imgs){imgs[i].style.maxWidth = '100%';imgs[i].style.height = 'auto';}</script>";
    //
    public static final String HOUR                                          = "hour";
    public static final String MINUTE                                        = "minute";
    public static final String SECOND                                        = "second";
    //cache key
    public static final String SESSION_KEY_LOGIN_USER                        = "SESSION_KEY_LOGIN_USER";
    public static final String CACHE_KEY_USER_TOKEN                          = "CACHE_KEY_USER_TOKEN_";
    public static final String CACHE_KEY_SMS_CODE                            = "CACHE_KEY_SMS_CODE_";
    public static final String CACHE_KEY_PROPERTY_VAL                        = "CACHE_KEY_PROPERTY_VAL_";
    public static final String CACHE_KEY_ACCESS_TOKEN_BY_CODE                = "CACHE_KEY_ACCESS_TOKEN_BY_CODE_";
    public static final String CACHE_KEY_ACCESS_TOKEN                        = "CACHE_KEY_ACCESS_TOKEN_";
    //短信token有效时间
    public static final String SMS_TOKEN_VALID_MINUTE                        = "sms.token.valid.minute";
    //
    public static final String MIN_AMOUNT_ELECTRICITY_CHARGE                 = "min_amount_electricity_charge";
    public static final String ELECTRICITY_CHARGE_STANDARD                   = "electricity_charge_standard";
    //
    public static final String ORDER_BY_TIME_DESC                            = "time-desc";
    public static final String ORDER_BY_SALE_DESC                            = "sale-desc";
    public static final String ORDER_BY_STAR_DESC                            = "star-desc";
    //微信公众号登陆
    public static final String CACHE_KEY_WX_REDIRECT_URL                     = "cache_key_wx_redirect_url_";
    public static final String CACHE_KEY_WX_BINDPHONE_URL                    = "cache_key_wx_bindphone_url_";
    public static final String CACHE_KEY_WX_DISPATCH_URL                     = "cache_key_wx_dispatch_url_";
    //短信目的
    public static final String PURPOSE_REG_VERIFY                            = "reg_verify"; //注册校验
    public static final String PURPOSE_EMAIL_MODIFY                          = "email_modify"; //邮箱校验
    public static final String PURPOSE_MOBILE_MODIFY                         = "mobile_modify"; //手机校验
    public static final String PURPOSE_PASSWORD_FORGOT                       = "password_forgot"; //密码找回
    //微信公众号支付ticket缓存key
    public static final String CACHE_KEY_WX_TICKET                           = "cache_key_wx_ticket_";
    //
    public static final String TEMPERATURE_OVERRUN_REMARK                    = "温度超限警报";
    public static final String TEMPERATURE_CHASSIS_OPENED_REMARK             = "机箱被打开警报";
    public static final String TEMPERATURE_POWER_FAILURE_REMARK              = "停电警报";
    public static final String TEMPERATURE_SMOKE_WARNING_REMARK              = "烟感报警警报";
    //财务分配
    //电费百分比
    public static final double FINANCIAL_MANAGEMENT_ELECTRICITY_FEES_PERCENT = 0.4;
    //除去电费剩余的钱
    public static final double FINANCIAL_MANAGEMENT_LEFT_PERCENT             = 1 - FINANCIAL_MANAGEMENT_ELECTRICITY_FEES_PERCENT;
    //平台管理费
    public static final double FINANCIAL_MANAGEMENT_PLATFORM_PERCENT         = FINANCIAL_MANAGEMENT_LEFT_PERCENT * 0.1;
    //投资人拿的钱
    public static final double FINANCIAL_MANAGEMENT_INVESTOR_PERCENT         = FINANCIAL_MANAGEMENT_LEFT_PERCENT * 0.5;
    //默认历史数据采样最多点数
    public static final String DEFAULT_MAX_DATA_COUNT                        = "500";
    //
    public static final String OVER_TIME_PAY_ORDER_JOB                       = "OVER_TIME_PAY_ORDER_JOB";
    public static final String OVER_TIME_OUT_ORDER_JOB                       = "OVER_TIME_OUT_ORDER_JOB";
    public static final String OVER_TIME_PAUSE_JOB                           = "OVER_TIME_PAUSE_JOB";
    public static final String OVER_TIME_STOP_JOB                            = "OVER_TIME_STOP_JOB";
    public static final String OVER_TIME_WILL_JOB                            = "OVER_TIME_WILL_JOB";
    //wx消息推送 默认url
    public static final String WX_TUI_URL                                    = "http://h5.jindoo.jopool.net/station/index.html?#/";
    //mqtt等待消息超时时间,单位s
    public static final long   MQTT_RESP_TIME_OUT                            = 40;

}
