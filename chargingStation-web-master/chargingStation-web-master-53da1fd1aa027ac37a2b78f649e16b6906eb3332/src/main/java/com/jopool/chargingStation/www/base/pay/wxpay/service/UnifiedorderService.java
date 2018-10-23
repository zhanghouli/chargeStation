package com.jopool.chargingStation.www.base.pay.wxpay.service;


import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderReqData;

public class UnifiedorderService extends BaseService {

    public UnifiedorderService(WXAbstractConfig wxConfig) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(WXAbstractConfig.UNIFIEDORDER_API, wxConfig);
    }

    /**
     * 统一下单服务
     *
     * @param unifiedorderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedorderReqData unifiedorderReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedorderReqData);

        return responseString;
    }
}
