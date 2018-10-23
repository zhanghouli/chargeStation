package com.jopool.chargingStation.www.controller.api.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.alipayNew.config.AlipayConfig;
import com.jopool.chargingStation.www.base.pay.alipayNew.entity.AlipayRepParam;
import com.jopool.chargingStation.www.base.pay.common.PlatformEnum;
import com.jopool.chargingStation.www.base.pay.wxpay.common.TradeType;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.request.WxpayPrepareReq;
import com.jopool.chargingStation.www.base.pay.wxpay.response.WxPayPrepareResp;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.controller.web.IndexController;
import com.jopool.chargingStation.www.enums.RechargeOrderTypeEnum;
import com.jopool.chargingStation.www.enums.StationPortStatusEnum;
import com.jopool.chargingStation.www.service.*;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.MathUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.*;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.PayUtil;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

/**
 * @Package Name : com.jopool.chargingStation.www.controller.api.common
 * @Author : soupcat
 * @Creation Date : 2017/8/24 下午6:25
 */
@RestController
@RequestMapping("/api/common/pay")
public class ApiCommonPayController extends BaseController {
    private static final String    ERROR_URL = "redirect:/error/error.html";
    private static final Logger    log       = Logger.getLogger(IndexController.class);
    private static final String    SUCCESS   = "success";
    private static final String    FAIL      = "fail";
    /**
     * 重复通知过滤
     */
    private static       ExpireKey expireKey = new DefaultExpireKey();
    @Resource
    private PassportService passportService;
    @Resource
    private CommonService   commonService;
    @Resource
    private OrderService    orderService;
    @Resource
    private PayAliService   payAliService;
    @Resource
    private PayWxService    payWxService;
    @Resource
    private StationService  stationService;

    /**
     * 订单支付预处理
     * 合并订单 多个订单合并 [充电站未使用]
     *
     * @param baseParam 目前充电站 因为充值没有订单order数据 所以建立中间表rechargeOrder 统一两种订单
     * @param orderIds  其余需要订单合并时候使用
     * @return
     */
    @RequestMapping("payPrepare.htm")
    public Result payPrepare(BaseParam baseParam, String orderIds) {
        validateParam(orderIds);
        Carowner carowner = passportService.getCarownerByPassportId(baseParam.getPassportId());
        if (null == carowner) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        String openId = carowner.getOpenId();
        //支付订单 该项目直接获取订单  其余项目根据需求可以合并订单返回id
        RechargeOrder rechargeOrder = orderService.getRechargeOrderById(orderIds);
        if (null == rechargeOrder) {
            return new Result(Code.ERROR, CodeMessage.ORDER_NOT_EXIST);
        }
        if (RechargeOrderTypeEnum.ORDER.getValue().equals(rechargeOrder.getType())) {
            Order order = orderService.getByOrderId(rechargeOrder.getOrderId());
            if (order != null) {
                StationPort stationPort = stationService.getPortById(order.getStationPortId());
                if (stationPort != null && !StationPortStatusEnum.FREE.getValue().equals(stationPort.getStatus())) {
                    return new Result(Code.ERROR, "该充电口已被占用或故障，请选择其他充电口重新下单");
                }
            }
        }
        return new Result(Code.SUCCESS, createJsonMap(new String[]{"tradeNo", "openId"}, new Object[]{rechargeOrder.getId(), openId}));
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>支付宝<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//
    //--------------------------------------------------------------------------------//


    /**
     * 支付宝支付  统一下单
     *
     * @param baseParam
     * @param tradeNo
     * @return
     */
    @RequestMapping("gotoAlipay.htm")
    public Result alipayPrepare(BaseParam baseParam, String tradeNo) {
        validateParam(tradeNo);
        //支付订单
        RechargeOrder rechargeOrder = orderService.getRechargeOrderById(tradeNo);
        if (null == rechargeOrder) {
            return new Result(Code.ERROR, CodeMessage.ORDER_NOT_EXIST);
        }
        String appId = AlipayConfig.APPID;
        String rsaPrivateKey = AlipayConfig.RSA_PRIVATE_KEY;
        String aliPayPublicKey = AlipayConfig.ALIPAY_PUBLIC_KEY;
        String notifyUrl = AlipayConfig.notify_url;
        AliConfig aliConfig = payAliService.getByCreationTimeDesc();
        if (aliConfig != null) {
            appId = aliConfig.getAppId();
            rsaPrivateKey = aliConfig.getPrivateKey();
            aliPayPublicKey = aliConfig.getPublicKey();
            notifyUrl = aliConfig.getNotifyUrl();
        }
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, rsaPrivateKey, "json", AlipayConfig.CHARSET, aliPayPublicKey, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(orderService.getPayBody(rechargeOrder));
        model.setSubject(orderService.getPaySubject(rechargeOrder));
        model.setOutTradeNo(tradeNo);
//        model.setTimeoutExpress("30m");
        model.setTotalAmount(MathUtils.div(rechargeOrder.getPayment(), 100, 2) + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return new Result(Code.SUCCESS, createJsonMap("body", response.getBody()));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return new Result(Code.ERROR);
    }

    /**
     * 支付宝-支付回调
     *
     * @param request
     * @return
     */
    @RequestMapping("doAlipay.htm")
    public String doAlipay(HttpServletRequest request) {
        AlipayRepParam response = new AlipayRepParam(request);
        AlipayResponse alipayResponse = response.getResponse();
        try {
            boolean flag = AlipaySignature.rsaCheckV1(response.getParams(), AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if (flag && AlipayRepParam.TRADE_STATUS_SUCCESS.equals(alipayResponse.getTradeStatus()) && payAliService.alipay(alipayResponse)) {
                return SUCCESS;
            } else {
                return FAIL;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return FAIL;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>微信支付<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//
    //--------------------------------------------------------------------------------//

    /**
     * 微信支付统一下单,支持:PC,APP,GZH,WXXCX
     *
     * @param wxpayPrepareReq
     * @return
     */
    @RequestMapping("gotoWxpay.htm")
    public Result wxpayPrepare(HttpServletRequest request, HttpServletResponse response, BaseParam baseParam, final WxpayPrepareReq wxpayPrepareReq) {
        validateParam(baseParam.getPassportId());
        validateParam(wxpayPrepareReq.getTradeNo());
        //支付订单
        RechargeOrder rechargeOrder = orderService.getRechargeOrderById(wxpayPrepareReq.getTradeNo());
        if (null == rechargeOrder) {
            return new Result(Code.ERROR, CodeMessage.ORDER_NOT_EXIST);
        }
        //
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, wxpayPrepareReq.getPlatform());
        if (null == payWx) {
            return new Result(Code.ERROR, "微信支付配置不存在");
        }
        //
        final WXAbstractConfig config = WXAbstractConfig.obtain(payWx);
        //
        final Result result = new Result();
        final WxPayPrepareResp payWxResp = new WxPayPrepareResp();
        payWxResp.setOutTradeNo(wxpayPrepareReq.getTradeNo());
        result.setResult(payWxResp);
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid(config.getAppId());
        unifiedorder.setMch_id(config.getMchId());
        unifiedorder.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        unifiedorder.setOut_trade_no(rechargeOrder.getId());
        unifiedorder.setTotal_fee(rechargeOrder.getPayment() + "");//单位分
        unifiedorder.setSpbill_create_ip(request.getRemoteAddr());//IP
        unifiedorder.setNotify_url(config.getNotifyUrl());
        unifiedorder.setAttach(wxpayPrepareReq.getPlatform());
        if (PlatformEnum.APP.getValue().equals(wxpayPrepareReq.getPlatform())) {
            unifiedorder.setTrade_type(TradeType.APP);
            unifiedorder.setBody("好驿达充电-" + orderService.getPayBody(rechargeOrder));
        } else if (PlatformEnum.WEB.getValue().equals(wxpayPrepareReq.getPlatform())) {
            unifiedorder.setTrade_type(TradeType.NATIVE);
            unifiedorder.setBody("好驿达充电-" + orderService.getPayBody(rechargeOrder));
        } else if (PlatformEnum.GZH.getValue().equals(wxpayPrepareReq.getPlatform())) {
            unifiedorder.setTrade_type(TradeType.JSAPI);
            unifiedorder.setBody("好驿达充电-" + orderService.getPayBody(rechargeOrder));
            unifiedorder.setOpenid(wxpayPrepareReq.getOpenId());
        } else if (PlatformEnum.WXXCX.getValue().equals(wxpayPrepareReq.getPlatform())) {
            unifiedorder.setTrade_type(TradeType.JSAPI);
            unifiedorder.setBody("好驿达充电-" + orderService.getPayBody(rechargeOrder));
            unifiedorder.setOpenid(wxpayPrepareReq.getOpenId());
        }
        UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, config.getKey());

        //@since 2.8.5  API返回数据签名验证
        if (unifiedorderResult.getSign_status() != null && unifiedorderResult.getSign_status()) {
            //TODO 暂时使用两个
            if (PlatformEnum.APP.getValue().equals(wxpayPrepareReq.getPlatform())) {
                MchPayApp appData = PayUtil.generateMchAppData(unifiedorderResult.getPrepay_id(), config.getAppId(), config.getMchId(), config.getKey());
                payWxResp.setAppRequestData(appData);
            } else if (PlatformEnum.GZH.getValue().equals(wxpayPrepareReq.getPlatform())) {
                String jsonStr = PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), config.getAppId(), config.getKey());
                JSONObject json = JSON.parseObject(jsonStr);
                payWxResp.setMpRequestData(json);
            }
            payWxResp.setCodeUrl(unifiedorderResult.getCode_url());
            result.setCode(Code.SUCCESS);
            return result;
        }
        return new Result(Code.ERROR);
    }

    /**
     * 微信-支付回调
     *
     * @param request
     * @return
     */
    @RequestMapping("doWxpay.htm")
    public void doWxpay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取请求数据
        String xmlData = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));
        validateParam(xmlData);
        //将XML转为MAP,确保所有字段都参与签名验证
        Map<String, String> mapData = XMLConverUtil.convertToMap(xmlData);
        //转换数据对象
        MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xmlData);
        //已处理 去重
        if (expireKey.exists(payNotify.getTransaction_id())) {
            return;
        }
        //@since 2.8.5
        payNotify.buildDynamicField(mapData);
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, payNotify.getAttach());
        //签名验证
        if (SignatureUtil.validateSign(mapData, payWx.getPayKey())) {
            WxPayResponse wxpayResponse = new WxPayResponse(payNotify);
            boolean r = orderService.updateOrderPaySuccess(null, wxpayResponse);
            MchBaseResult baseResult = new MchBaseResult();
            if (r) {
                expireKey.add(payNotify.getTransaction_id());
                baseResult.setReturn_code("SUCCESS");
                baseResult.setReturn_msg("OK");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            } else {
                baseResult.setReturn_code("FAIL");
                baseResult.setReturn_msg("ERROR");
                response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
            }
        } else {
            MchBaseResult baseResult = new MchBaseResult();
            baseResult.setReturn_code("FAIL");
            baseResult.setReturn_msg("ERROR");
            response.getOutputStream().write(XMLConverUtil.convertToXML(baseResult).getBytes());
        }
    }


    /**
     * 查询支付状态
     *
     * @return
     */
    @RequestMapping("wxpayOrderQuery.htm")
    public Result wxpayOrderQuery(BaseParam baseParam, String tradeNo) {
        validateParam(tradeNo);
        //TODO 暂时用于APP查询
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, PlatformEnum.APP.getValue());
        MchOrderquery mchOrderquery = new MchOrderquery();
        mchOrderquery.setAppid(payWx.getPayAppid());
        mchOrderquery.setMch_id(payWx.getMchId());
        mchOrderquery.setNonce_str(UUID.randomUUID().toString().replace("-", ""));
        mchOrderquery.setOut_trade_no(tradeNo);
        MchOrderInfoResult result = PayMchAPI.payOrderquery(mchOrderquery, payWx.getPayKey());
        if (!("SUCCESS").equals(result.getReturn_code())) {
            return new Result(Code.ERROR);
        }
        if (!("SUCCESS").equals(result.getTrade_state())) {
            return new Result(Code.ERROR);
        }
        return new Result(Code.SUCCESS);
    }

}
