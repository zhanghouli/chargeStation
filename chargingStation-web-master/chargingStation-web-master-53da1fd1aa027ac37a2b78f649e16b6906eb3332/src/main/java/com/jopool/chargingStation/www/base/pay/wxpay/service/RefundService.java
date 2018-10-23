package com.jopool.chargingStation.www.base.pay.wxpay.service;

import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.refund_protocol.RefundReqData;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:04
 */
public class RefundService extends BaseService{

    public RefundService(WXAbstractConfig wxConfig) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(WXAbstractConfig.REFUND_API, wxConfig);
    }

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(RefundReqData refundReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(refundReqData);

        return responseString;
    }

}
