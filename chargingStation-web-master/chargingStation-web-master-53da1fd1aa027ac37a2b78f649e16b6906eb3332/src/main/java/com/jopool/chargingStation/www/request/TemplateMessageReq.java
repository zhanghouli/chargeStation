package com.jopool.chargingStation.www.request;

import com.jopool.chargingStation.www.base.pay.wxpay.message.TemplateData;
import com.jopool.chargingStation.www.base.pay.wxpay.message.TemplateMessage;

/**
 * Created by synn on 2017/11/27.
 */
public class TemplateMessageReq extends TemplateMessage {

    public TemplateMessageReq(String touser, String template_id, String url,String topcolor, TemplateData data) {
        super(touser, template_id, url,topcolor, data);
    }
}
