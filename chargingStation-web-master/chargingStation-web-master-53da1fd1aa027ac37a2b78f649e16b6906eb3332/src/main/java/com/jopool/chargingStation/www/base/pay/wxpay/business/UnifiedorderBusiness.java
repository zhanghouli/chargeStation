package com.jopool.chargingStation.www.base.pay.wxpay.business;

import com.jopool.chargingStation.www.base.pay.wxpay.common.Signature;
import com.jopool.chargingStation.www.base.pay.wxpay.common.Util;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.jopool.chargingStation.www.base.pay.wxpay.service.UnifiedorderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gexin on 16/1/12.
 */
public class UnifiedorderBusiness extends BaseBusiness {
    private static final Logger log = LoggerFactory.getLogger(UnifiedorderBusiness.class);
    private UnifiedorderService unifiedorderService;

    public UnifiedorderBusiness(WXAbstractConfig wxConfig) {
        this.wxConfig = wxConfig;
        try {
            this.unifiedorderService = new UnifiedorderService(wxConfig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface ResultListener {

        //API返回ReturnCode不合法，唯一下单请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(UnifiedorderResData unifiedorderResData);

        //API返回ReturnCode为FAIL，唯一下单API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(UnifiedorderResData unifiedorderResData);

        //唯一下单请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(UnifiedorderResData unifiedorderResData);

        //失败
        void onFail(UnifiedorderResData unifiedorderResData);

        //成功
        void onSuccess(UnifiedorderResData unifiedorderResData);

    }

    /**
     * 请求
     *
     * @param unifiedorderReqData
     * @param resultListener
     * @throws Exception
     */
    public void run(UnifiedorderReqData unifiedorderReqData, ResultListener resultListener) {

        //--------------------------------------------------------------------
        //构造请求“唯一下单API”所需要提交的数据
        //--------------------------------------------------------------------

        long costTimeStart = System.currentTimeMillis();

        String unifiedorderServiceResponseString = null;
        try {
            unifiedorderServiceResponseString = unifiedorderService.request(unifiedorderReqData);
        } catch (Exception e) {
            resultListener.onFailByReturnCodeError(null);
        }

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.debug("api请求总耗时：" + totalTimeCost + "ms");

        //打印回包数据
        log.debug(unifiedorderServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        UnifiedorderResData unifiedorderResData = (UnifiedorderResData) Util.getObjectFromXML(unifiedorderServiceResponseString, UnifiedorderResData.class);

        log.error("【唯一下单返回】" + unifiedorderServiceResponseString);
        if (unifiedorderResData == null || unifiedorderResData.getReturn_code() == null) {
            log.error("【唯一下单失败】唯一下单请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            resultListener.onFailByReturnCodeError(unifiedorderResData);
            return;
        }

        if (unifiedorderResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.error("【唯一下单失败】唯一下单API系统返回失败，请检测Post给API的数据是否规范合法");
            resultListener.onFailByReturnCodeFail(unifiedorderResData);
            return;
        } else {
            log.debug("唯一下单API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            try {
                if (!Signature.checkIsSignValidFromResponseString(wxConfig, unifiedorderServiceResponseString)) {
                    log.error("【唯一下单失败】唯一下单请求API返回的数据签名验证失败，有可能数据被篡改了");
                    resultListener.onFailBySignInvalid(unifiedorderResData);
                    return;
                }
            } catch (Exception e) {
                resultListener.onFailBySignInvalid(unifiedorderResData);
            }

            if (unifiedorderResData.getResult_code().equals("SUCCESS")) {
                log.debug("【一次性唯一下单成功】");
                resultListener.onSuccess(unifiedorderResData);
            } else {
                log.debug("业务返回失败");
                resultListener.onFail(unifiedorderResData);
            }
        }
    }

}
