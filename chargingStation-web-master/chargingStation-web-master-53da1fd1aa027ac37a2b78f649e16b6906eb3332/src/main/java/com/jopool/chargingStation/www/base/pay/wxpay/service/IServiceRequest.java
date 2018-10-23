package com.jopool.chargingStation.www.base.pay.wxpay.service;

import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:16
 * 这里定义服务层需要请求器标准接口
 */
public interface IServiceRequest {
    void init(WXAbstractConfig wxConfig) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException ;
    //Service依赖的底层https请求器必须实现这么一个接口
    String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

}
