package com.jopool.chargingStation.www.controller.api.common;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.common.PlatformEnum;
import com.jopool.chargingStation.www.base.pay.wxpay.WXPay;
import com.jopool.chargingStation.www.base.pay.wxpay.business.UnifiedorderBusiness;
import com.jopool.chargingStation.www.base.pay.wxpay.common.Signature;
import com.jopool.chargingStation.www.base.pay.wxpay.common.TradeType;
import com.jopool.chargingStation.www.base.pay.wxpay.common.Util;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXAbstractConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.config.WXUrlConfig;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.BaseResData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.common_protocol.NotifyResData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderReqData;
import com.jopool.chargingStation.www.base.pay.wxpay.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.jopool.chargingStation.www.base.pay.wxpay.request.*;
import com.jopool.chargingStation.www.base.pay.wxpay.response.*;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.controller.CommonFileController;
import com.jopool.chargingStation.www.controller.web.IndexController;
import com.jopool.chargingStation.www.enums.PassportStatusEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.response.LoginResp;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.PayWxService;
import com.jopool.chargingStation.www.vo.CarownerVo;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.HttpUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.URLUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Package Name : com.jopool.chargingStation.www.controller.api.common
 * @Author : soupcat
 * @Creation Date : 2017/8/24 下午6:25
 */
@RestController
@RequestMapping("/api/common/wx")
public class ApiCommonWeixinBindController extends BaseController {
    private static final String ERROR_URL = "redirect:/error/error.html";
    private static final Logger log       = Logger.getLogger(IndexController.class);
    @Resource
    private PayWxService    payWxService;
    @Resource
    private PassportService passportService;
    @Resource
    private CommonService   commonService;
    @Resource
    private OrderService    orderService;

    /**
     * 跳转
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4555350&src=contextnavpagetreemode
     *
     * @param request
     * @param redirectUrl  登录成功后重定向的地址
     * @param bindPhoneUrl 如果没有绑定过那就重定向到绑定手机号页面
     * @return
     */
    @RequestMapping("jump.htm")
    public ModelAndView jump(HttpServletRequest request, String redirectUrl, String bindPhoneUrl, String dispatchUrl) {
        validateParam(redirectUrl);
        String key = String.valueOf(Constants.SYSTEM_ID);
        cacheBean.put(Constants.CACHE_KEY_WX_REDIRECT_URL + key, redirectUrl);
        cacheBean.put(Constants.CACHE_KEY_WX_BINDPHONE_URL + key, bindPhoneUrl);
        cacheBean.put(Constants.CACHE_KEY_WX_DISPATCH_URL + key, dispatchUrl);
        //支付配置信息
        WxConfig wxConfig = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, PlatformEnum.GZH.getValue());
        //1.访问authorize获取code
        AuthorizeReq req = new AuthorizeReq(true, key, wxConfig);
        return new ModelAndView("redirect:" + URLUtils.addQuery(WXUrlConfig.WX_AUTH_URL, req) + "#wechat_redirect");
    }

    /**
     * 微信回调oauth2
     *
     * @param request
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("oauth2.htm")
    public ModelAndView oauth2(HttpServletRequest request, String code, String state) {
        System.out.println("---------oauth2.htm,code=" + code + ",state=" + state);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
            return new ModelAndView(ERROR_URL);
        }
        //如果dispatchurl存在，就直接跳出去
        String dispatchUrl = (String) cacheBean.get(Constants.CACHE_KEY_WX_DISPATCH_URL + state);
        if (!StringUtils.isEmpty(dispatchUrl)) {
            cacheBean.remove(Constants.CACHE_KEY_WX_DISPATCH_URL + state);
            return new ModelAndView("redirect:" + URLUtils.addQueryString(dispatchUrl, new String[]{"code", "state"}, new Object[]{code, state}));
        }
        String platformId = state;
        AccessTokenResp accessTokenResp = commonService.getAccessToken(code, platformId);
        if (null == accessTokenResp || StringUtils.isEmpty(accessTokenResp.getOpenid())) {
            return new ModelAndView(ERROR_URL);
        }
        //
        //3.系统是否存在openid
        synchronized (accessTokenResp.getOpenid().intern()) {
            WxInfo wxInfo = passportService.getWxInfoByOpenId(accessTokenResp.getOpenid());
            if (null == wxInfo) {
                //4.拉取用户信息
                UserInfoReq userInfoReq = new UserInfoReq(accessTokenResp.getAccess_token(), accessTokenResp.getOpenid());
                JSON userInfoRespJson = HttpUtils.getJsonByGet(URLUtils.addQuery(WXUrlConfig.WX_USERINFO_URL, userInfoReq));
                UserInfoResp userInfoResp = JSON.toJavaObject(userInfoRespJson, UserInfoResp.class);
                if (null == userInfoResp) {
                    return new ModelAndView(ERROR_URL);
                }
                wxInfo = userInfoResp.parseWxInfo(platformId);
                passportService.addWxInfo(wxInfo);
            }
        }
        String bindPhoneUrl = (String) cacheBean.get(Constants.CACHE_KEY_WX_BINDPHONE_URL + state);
        String url = URLUtils.addQueryString(bindPhoneUrl, new String[]{"openId"}, new Object[]{accessTokenResp.getOpenid()});
        return new ModelAndView("redirect:" + url);
    }


    //TODO 微信相关

    /**
     * 微信绑定手机或者验证码登录
     * <p>
     * openId not null -> 微信验证登录：shopId<0 and openId not null
     * shopId>0 -> 手机号登录：shopId>0 and ignore openId
     *
     * @param openId
     * @return
     */
    @RequestMapping("bind.htm")
    public Result bind(HttpServletRequest request, String openId, String phone, String smsCode) {
        validateParam(phone, smsCode);
        if (!Constants.NO_VERIFICATION_PHONE.equals(phone)) {
            CommonTokenAuthAider tokenAuthAider = commonService.getTokenAuthAiderByPhone(phone);
            if (null == tokenAuthAider || !tokenAuthAider.getAuthSn().equals(smsCode) || new Date().after(tokenAuthAider.getAuthExpiredDatetime())) {
                return new Result(Code.ERROR, CodeMessage.SMS_CODE_ERROR);
            }
        }
        //
        Passport passport = null;
        Carowner carowner = null;
        CommonPicture commonPicture = null;
        WxInfo wxInfo = null;
        //微信验证登录
        if (!StringUtils.isEmpty(openId)) {
            //判断openId对应的数据是否存在
            wxInfo = passportService.getWxInfoByOpenId(openId);
            if (null == wxInfo) {
                return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
            }

        }
        CarownerVo carOwnerVo = passportService.getCarownerVoByPhone(phone);
        //如果账号已经存在直接返回
        if (null != carOwnerVo) {
            passport = carOwnerVo.getPassport();
            carowner = carOwnerVo.getCarowner();
            if (PassportStatusEnum.DISABLE.getValue().equals(passport.getStatus())) {
                return new Result(Code.ERROR, CodeMessage.ACCOUNT_DISABLE);
            }
            if (!StringUtils.isEmpty(openId) && !openId.equals(carowner.getOpenId())) {
                carowner.setOpenId(openId);
                passportService.modifyCarOwner(carowner);
            }
        } else {
            //validate
            Passport passportExist = passportService.getByPhoneAndType(phone, PassportTypeEnum.CAROWNER.getValue());
            if (null != passportExist) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //passport
            passport = new Passport(phone, wxInfo);
            passportService.add(passport);
            //carowner
            carowner = new Carowner(passport, wxInfo);
            passportService.addCarowner(carowner);

            CommonFileController commonFileController = new CommonFileController();
            //添加图片
            if (!StringUtils.isEmpty(wxInfo.getHeadimgurl())) {
                commonPicture = uploadWxImage(request, wxInfo.getHeadimgurl(), passport);
            }
        }
        LoginResp resp = new LoginResp(passport, passport.getId());
        return new Result(Code.SUCCESS, resp);
    }

    /**
     * Wx公众号支付准备接口1(获取一些基本参数),这里还没有开始统一下单哦
     *
     * @param platform
     * @param url
     * @return
     */
    @RequestMapping("wxmppayPrepare.htm")
    public Result wxmppayPrepare(BaseParam baseParam, String platform, String url) {
        validateParam(baseParam.getPassportId());
        validateParam(platform, url);
        //
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, platform);
        if (null == payWx) {
            return new Result(Code.ERROR, "微信支付配置不存在");
        }
        //
        String ticket = (String) cacheBean.get(Constants.CACHE_KEY_WX_TICKET + Constants.SYSTEM_ID);
        if (StringUtils.isEmpty(ticket)) {
            //token
            TokenReq tokenReq = new TokenReq(payWx.getPayAppid(), payWx.getPaySecret());
            JSON tokenRespJson = HttpUtils.getJsonByGet(URLUtils.addQuery(WXUrlConfig.WX_TOKEN_URL, tokenReq));
            TokenResp tokenResp = JSON.toJavaObject(tokenRespJson, TokenResp.class);
            if (null == tokenResp || StringUtils.isEmpty(tokenResp.getAccess_token())) {
                return new Result(Code.ERROR, "获取access_token失败");
            }
            //ticket
            TicketReq ticketReq = new TicketReq(tokenResp.getAccess_token());
            JSON ticketRespJson = HttpUtils.getJsonByGet(URLUtils.addQuery(WXUrlConfig.WX_GET_TICKET_URL, ticketReq));
            TicketResp ticketResp = JSON.toJavaObject(ticketRespJson, TicketResp.class);
            if (null == ticketResp || StringUtils.isEmpty(ticketReq.getAccess_token())) {
                return new Result(Code.ERROR);
            }
            //
            ticket = ticketResp.getTicket();
            cacheBean.put(Constants.CACHE_KEY_WX_TICKET + Constants.SYSTEM_ID, ticket, 7000, TimeUnit.SECONDS);
        }
        WXMPPayPrepareResp resp = new WXMPPayPrepareResp(payWx.getPayAppid(), url, ticket);
        return new Result(Code.SUCCESS, resp);
    }


    /**
     * 微信支付统一下单,支持:PC,APP,GZH,WXXCX
     *
     * @param wxpayPrepareReq
     * @return
     */
    @RequestMapping("wxpayPrepare.htm")
    public Result wxpayPrepare(BaseParam baseParam, final WxpayPrepareReq wxpayPrepareReq) {
        validateParam(baseParam.getPassportId());
        validateParam(wxpayPrepareReq.getBody(), wxpayPrepareReq.getOutTradeNo(), wxpayPrepareReq.getTotalFee(), wxpayPrepareReq.getSubject(), wxpayPrepareReq.getPlatform());
        //
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, wxpayPrepareReq.getPlatform());
        if (null == payWx) {
            return new Result(Code.ERROR, "微信支付配置不存在");
        }
        //
        final WXAbstractConfig config = WXAbstractConfig.obtain(payWx);
        WXPay wxPay = new WXPay(config);
        //
        final Result result = new Result();
        final WxPayPrepareResp payWxResp = new WxPayPrepareResp();
        payWxResp.setOutTradeNo(wxpayPrepareReq.getOutTradeNo());
        result.setResult(payWxResp);

        double totalFee = Double.parseDouble(wxpayPrepareReq.getTotalFee());
//        totalFee = totalFee * 100;
        //
        String device_info = "";//PC网页或公众号内支付请传"WEB"
        String trade_type = "";//取值如下：JSAPI，NATIVE，APP，详细说明见参数规定 NATIVE原生扫码支付
        if (PlatformEnum.APP.getValue().equals(wxpayPrepareReq.getPlatform())) {
            trade_type = TradeType.APP;
            device_info = "";
        } else if (PlatformEnum.WEB.getValue().equals(wxpayPrepareReq.getPlatform())) {
            trade_type = TradeType.NATIVE;
            device_info = "WEB";
        } else if (PlatformEnum.GZH.getValue().equals(wxpayPrepareReq.getPlatform())) {
            trade_type = TradeType.JSAPI;
            device_info = "WEB";
        } else if (PlatformEnum.WXXCX.getValue().equals(wxpayPrepareReq.getPlatform())) {
            trade_type = TradeType.JSAPI;
            device_info = "WEB";
        }
        //
        String openId = wxpayPrepareReq.getOpenId() != null ? wxpayPrepareReq.getOpenId() : "";
        final UnifiedorderReqData unifiedorderReqData = new UnifiedorderReqData(config, device_info, trade_type, wxpayPrepareReq.getSubject(), wxpayPrepareReq.getBody(), wxpayPrepareReq.getPlatform() + "," + Constants.SYSTEM_ID + "," + wxpayPrepareReq.getCouponId(), wxpayPrepareReq.getOutTradeNo(), (int) totalFee, openId);
        wxPay.doUnifiedorderBusiness(unifiedorderReqData, new UnifiedorderBusiness.ResultListener() {
            @Override
            public void onFailByReturnCodeError(UnifiedorderResData unifiedorderResData) {
                result.setCode(Code.ERROR);
                result.setMessage("请求逻辑错误");
            }

            @Override
            public void onFailByReturnCodeFail(UnifiedorderResData unifiedorderResData) {
                result.setCode(Code.ERROR);
                result.setMessage("系统返回失败");
            }

            @Override
            public void onFailBySignInvalid(UnifiedorderResData unifiedorderResData) {
                result.setCode(Code.ERROR);
                result.setMessage("签名验证失败");
            }

            @Override
            public void onFail(UnifiedorderResData unifiedorderResData) {
                result.setCode(Code.ERROR);
                result.setMessage("业务返回失败");
            }

            @Override
            public void onSuccess(UnifiedorderResData unifiedorderResData) {
                result.setCode(Code.SUCCESS);
                payWxResp.setCodeUrl(unifiedorderResData.getCode_url());
//                payWxResp.setAppReqData(new WxPayPrepareRespData(config, wxpayPrepareReq.getPlatform(), unifiedorderResData.getPrepay_id(), wxpayPrepareReq.getOutTradeNo()));
            }
        });
        return result;
    }

    /**
     * 微信-支付回调
     *
     * @param request
     * @return
     */
    @RequestMapping("doWxpay.htm")
    public void doWxpay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("doWxpay:start");
        //返回消息
        BaseResData resp = new BaseResData();
        resp.setReturn_msg("");
        resp.setReturn_code("FAIL");
        //处理接收消息
        ServletInputStream in = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String xml = null;
        StringBuilder sb = new StringBuilder();
        while ((xml = br.readLine()) != null) {
            sb.append(xml);
        }
        log.info("doWxpay:originData:" + sb.toString());
        NotifyResData notifyResData = (NotifyResData) Util.getObjectFromXML(sb.toString(), NotifyResData.class);
        //
        String[] platformAndShopId = StringUtils.split(notifyResData.getAttach(), ',');
        WxConfig payWx = payWxService.getByShopIdPlatform(Constants.SYSTEM_ID, platformAndShopId[0]);
        //
        WXAbstractConfig payConfig = WXAbstractConfig.obtain(payWx);
        boolean signCheck = Signature.checkIsSignValidFromResponseString(payConfig, sb.toString());
        if (notifyResData != null && "SUCCESS".equals(notifyResData.getReturn_code()) && "SUCCESS".equals(notifyResData.getResult_code()) && signCheck) {
            log.debug("doWxpay:resultSuccess");
            WxPayResponse wxpayResponse = new WxPayResponse(notifyResData);
            boolean r = orderService.updateOrderPaySuccess(null, wxpayResponse);
            if (r) {
                resp.setReturn_code("SUCCESS");
            }
        }
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(resp);
        log.info("doWxpay:postDataXML:" + postDataXML);
        response.getWriter().write(postDataXML);
    }

}
