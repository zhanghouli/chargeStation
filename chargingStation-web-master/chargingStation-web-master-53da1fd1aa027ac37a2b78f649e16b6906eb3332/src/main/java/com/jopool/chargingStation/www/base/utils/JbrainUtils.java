package com.jopool.chargingStation.www.base.utils;

import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.jbrainclientjava.JBClient;
import com.jopool.jbrainclientjava.JBConfig;
import com.jopool.jbrainclientjava.core.common.NoneResult;
import com.jopool.jbrainclientjava.core.utils.JBResult;
import com.jopool.jweb.enums.Code;

/**
 * Created by xuan on 16/12/7.
 */
public class JbrainUtils {
    /**
     * 发送验证码模版name,具体在jbrain后台配置
     */
    public static final String SMS_CODE = "SMS_CODE";

    static {
        JBClient.getIntance().init(new JBConfig(ApplicationConfigHelper.getJbrainPk(), ApplicationConfigHelper.getJbrainAppId(), ApplicationConfigHelper.getJbrainWebsite()));
    }

    /**
     * 发送短信验证码
     *
     * @param code
     * @param phone
     * @return
     */
    public static boolean sendSmsCode(String code, String phone) {
        JBResult<NoneResult> result = JBClient.getIntance().getSmsInvoker().sendSmsCode("SMS_CODE", "{\"code\":\"" + code + "\",\"time\":\"5\"}", phone);
        return result.getCode() == Code.SUCCESS.getValue();
    }
}
