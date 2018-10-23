package com.jopool.chargingStation.www.base.pay.alipayNew.entity;

import com.jopool.chargingStation.www.base.entity.AlipayResponse;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.StringUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gexin on 15/4/10.
 * 支付宝回调内容
 */
public class AlipayRepParam {
    public static final String TRADE_STATUS_FINISHED = "TRADE_FINISHED";
    public static final String TRADE_STATUS_SUCCESS  = "TRADE_SUCCESS";

    private Map<String, String> params;//所有参数
    private AlipayResponse response = new AlipayResponse();

    /**
     * 构造
     *
     * @param request
     */
    public AlipayRepParam(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            Field field = ReflectionUtils.findField(AlipayResponse.class, StringUtils.replaceUnderlineAndfirstToUpper(name, "_", ""));
            if (field == null) {
                continue;
            }
            field.setAccessible(true);
            if (field.getType() == Date.class) {
                ReflectionUtils.setField(field, response, DateUtils.string2Date(valueStr));
            } else {
                ReflectionUtils.setField(field, response, valueStr);
            }
        }
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public AlipayResponse getResponse() {
        return response;
    }

    public void setResponse(AlipayResponse response) {
        this.response = response;
    }
}
