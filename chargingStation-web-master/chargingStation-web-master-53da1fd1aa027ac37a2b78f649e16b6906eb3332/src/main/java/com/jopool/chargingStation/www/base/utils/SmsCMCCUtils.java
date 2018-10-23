package com.jopool.chargingStation.www.base.utils;

import com.jopool.jweb.entity.Sms;
import com.jopool.jweb.sms.kingtto.SmsKingttoUtil;
import com.jopool.jweb.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * Created by gexin on 2016/10/9.
 */
public class SmsCMCCUtils {
    private static Logger logger  = LoggerFactory.getLogger(SmsKingttoUtil.class);
    private static String HTTPURL = "http://121.199.28.96/stardy/send_jy.jsp";
    private static String USR     = "clzpeis";
    private static String PWD     = "clzpeis76";

    /**
     * 发送
     */
    public static boolean postMsg(Sms sms) {
        String[] phones = sms.getPhone();
        String content = sms.getContent();
        String resp = null;
        for (String phone : phones) {
            try {
                //http://121.199.28.96/send_jy.jsp?tjpc=123&usr=clzpeis&pwd=clzpeis76&mobile=15868474170&msg=%E6%B5%8B%E8%AF%95&yzm=4791
                StringBuffer buffer = new StringBuffer(HTTPURL);
                buffer.append("?tjpc=").append(System.currentTimeMillis());
                buffer.append("&usr=").append(USR);
                buffer.append("&pwd=").append(PWD);
                buffer.append("&mobile=").append(phone);
                buffer.append("&msg=").append(URLEncoder.encode(content, "gbk"));
                buffer.append("&yzm=").append(Integer.parseInt(phone.substring(phone.length() - 4, phone.length())) * 3 + 138);
                resp = HttpUtils.getStringByGet(buffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.debug("短信接口：向" + phone + "发送：" + content + ",返回：" + resp);
        }
        boolean result = false;
        if ("0".equals(resp)) {
            result = true;
        }
        return result;
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {
        postMsg(new Sms("尊敬的用户，您的验证码为1234，本验证码5分钟内有效，感谢您的使用！", new String[]{"15868474170"}));
    }
}
