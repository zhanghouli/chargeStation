package com.jopool.chargingStation.www.base.pay.wxpay.service;


import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.transfers_protocal.TransfersReqData;

public class TransfersService extends BaseService {

    public TransfersService(WXAbstractConfig wxConfig) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(WXAbstractConfig.TRANSFERS_API, wxConfig);
    }

    /**
     * 企业付款服务
     *
     * @return API返回的数据
     * @throws Exception
     */
    public String request(TransfersReqData transfersReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(transfersReqData);

        return responseString;
    }
}
