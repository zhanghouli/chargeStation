package com.jopool.chargingStation.www.base.pay.wxpay;

import com.jopool.chargingStation.www.base.pay.wxpay.business.*;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.downloadbill_protocol.DownloadBillReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.pay_protocol.ScanPayReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.refund_protocol.RefundReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.refund_query_protocol.RefundQueryReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.reverse_protocol.ReverseReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.transfers_protocal.TransfersReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.service.*;

/**
 * SDK总入口
 */
public class WXPay {
    private WXAbstractConfig wxConfig;

    public WXPay(WXAbstractConfig wxConfig) {
        this.wxConfig = wxConfig;
    }

    /**
     * 请求支付服务
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String requestScanPayService(ScanPayReqData scanPayReqData) throws Exception {
        return new ScanPayService(wxConfig).request(scanPayReqData);
    }

    /**
     * 请求支付查询服务
     *
     * @param scanPayQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String requestScanPayQueryService(ScanPayQueryReqData scanPayQueryReqData) {
        return new ScanPayQueryService(wxConfig).request(scanPayQueryReqData);
    }

    /**
     * 请求退款服务
     *
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String requestRefundService(RefundReqData refundReqData) throws Exception {
        return new RefundService(wxConfig).request(refundReqData);
    }

    /**
     * 请求退款查询服务
     *
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String requestRefundQueryService(RefundQueryReqData refundQueryReqData) throws Exception {
        return new RefundQueryService(wxConfig).request(refundQueryReqData);
    }

    /**
     * 请求撤销服务
     *
     * @param reverseReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String requestReverseService(ReverseReqData reverseReqData) throws Exception {
        return new ReverseService(wxConfig).request(reverseReqData);
    }

    /**
     * 请求对账单下载服务
     *
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String requestDownloadBillService(DownloadBillReqData downloadBillReqData) throws Exception {
        return new DownloadBillService(wxConfig).request(downloadBillReqData);
    }

    /**
     * 统一下单
     *
     * @param unifiedorderReqData
     * @param resultListener
     */
    public void doUnifiedorderBusiness(UnifiedorderReqData unifiedorderReqData, UnifiedorderBusiness.ResultListener resultListener) {
        new UnifiedorderBusiness(wxConfig).run(unifiedorderReqData, resultListener);
    }

    /**
     * 企业支付
     *
     * @param transfersReqData
     * @param resultListener
     */
    public void doTransfersBusiness(TransfersReqData transfersReqData, TransfersBusiness.ResultListener resultListener) {
        new TransfersBusiness(wxConfig).run(transfersReqData, resultListener);
    }

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     *
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public void doScanPayBusiness(ScanPayReqData scanPayReqData, ScanPayBusiness.ResultListener resultListener) throws Exception {
        new ScanPayBusiness(wxConfig).run(scanPayReqData, resultListener);
    }

    /**
     * 调用退款业务逻辑
     *
     * @param refundReqData  这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    public void doRefundBusiness(RefundReqData refundReqData, RefundBusiness.ResultListener resultListener) throws Exception {
        new RefundBusiness(wxConfig).run(refundReqData, resultListener);
    }

    /**
     * 运行退款查询的业务逻辑
     *
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener     商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public void doRefundQueryBusiness(RefundQueryReqData refundQueryReqData, RefundQueryBusiness.ResultListener resultListener) throws Exception {
        new RefundQueryBusiness(wxConfig).run(refundQueryReqData, resultListener);
    }

    /**
     * 请求对账单下载服务
     *
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener      商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    public void doDownloadBillBusiness(DownloadBillReqData downloadBillReqData, DownloadBillBusiness.ResultListener resultListener) throws Exception {
        new DownloadBillBusiness(wxConfig).run(downloadBillReqData, resultListener);
    }

    public WXAbstractConfig getWxConfig() {
        return wxConfig;
    }

    public void setWxConfig(WXAbstractConfig wxConfig) {
        this.wxConfig = wxConfig;
    }
}
