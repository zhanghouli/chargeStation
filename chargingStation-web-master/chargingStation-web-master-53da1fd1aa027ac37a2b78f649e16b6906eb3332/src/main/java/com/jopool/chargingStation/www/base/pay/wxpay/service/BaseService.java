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
 * Time: 15:44
 * 服务的基类
 */
public class BaseService {

    //API的地址
    private String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

    public BaseService(String api, WXAbstractConfig wxConfig) {
        apiURL = api;
        try {
            Class c = Class.forName(WXAbstractConfig.HttpsRequestClassName);
            serviceRequest = (IServiceRequest) c.newInstance();
            serviceRequest.init(wxConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String sendPost(Object xmlObj) {
        String result = "";
        try {
            result = serviceRequest.sendPost(apiURL, xmlObj);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     *
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request) {
        serviceRequest = request;
    }
}
