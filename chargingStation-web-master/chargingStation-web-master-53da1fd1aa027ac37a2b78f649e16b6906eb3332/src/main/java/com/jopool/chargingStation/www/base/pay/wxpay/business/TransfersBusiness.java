package com.jopool.chargingStation.www.base.pay.wxpay.business;

import com.jopool.chargingStation.www.base.pay.wxpay.common.Util;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.transfers_protocal.TransfersReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.transfers_protocal.TransfersResData;
import com.jopool.chargingStation.www.base.pay.wxpay.service.TransfersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gexin on 16/1/12.
 */
public class TransfersBusiness extends BaseBusiness {
    private static final Logger log = LoggerFactory.getLogger(TransfersBusiness.class);
    private TransfersService transfersService;

    public TransfersBusiness(WXAbstractConfig wxConfig) {
        this.wxConfig = wxConfig;
        try {
            this.transfersService = new TransfersService(wxConfig);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface ResultListener {

        //API返回ReturnCode不合法，企业付款请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问
        void onFailByReturnCodeError(TransfersResData transfersResData);

        //API返回ReturnCode为FAIL，企业付款API系统返回失败，请检测Post给API的数据是否规范合法
        void onFailByReturnCodeFail(TransfersResData transfersResData);

        //企业付款请求API返回的数据签名验证失败，有可能数据被篡改了
        void onFailBySignInvalid(TransfersResData transfersResData);

        //失败
        void onFail(TransfersResData transfersResData);

        //成功
        void onSuccess(TransfersResData transfersResData);

    }

    /**
     * 请求
     *
     * @param transfersReqData
     * @param resultListener
     * @throws Exception
     */
    public void run(TransfersReqData transfersReqData, ResultListener resultListener) {

        //--------------------------------------------------------------------
        //构造请求“企业付款API”所需要提交的数据
        //--------------------------------------------------------------------

        long costTimeStart = System.currentTimeMillis();

        String transfersServiceResponseString = null;
        try {
            transfersServiceResponseString = transfersService.request(transfersReqData);
        } catch (Exception e) {
            resultListener.onFailByReturnCodeError(null);
        }

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.debug("api请求总耗时：" + totalTimeCost + "ms");

        //打印回包数据
        log.debug(transfersServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        TransfersResData transfersResData = (TransfersResData) Util.getObjectFromXML(transfersServiceResponseString, TransfersResData.class);

        log.error("【企业付款】" + transfersServiceResponseString);
        if (transfersResData == null || transfersResData.getReturn_code() == null) {
            log.error("【企业付款失败】企业付款请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            resultListener.onFailByReturnCodeError(transfersResData);
            return;
        }

        if (transfersResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.error("【企业付款失败】企业付款API系统返回失败，请检测Post给API的数据是否规范合法");
            resultListener.onFailByReturnCodeFail(transfersResData);
            return;
        } else {
            log.debug("企业付款API系统成功返回数据");
            if (transfersResData.getResult_code().equals("SUCCESS")) {
                log.debug("【一次性企业付款成功】");
                resultListener.onSuccess(transfersResData);
            } else {
                log.debug("业务返回失败");
                resultListener.onFail(transfersResData);
            }
        }
    }

}
