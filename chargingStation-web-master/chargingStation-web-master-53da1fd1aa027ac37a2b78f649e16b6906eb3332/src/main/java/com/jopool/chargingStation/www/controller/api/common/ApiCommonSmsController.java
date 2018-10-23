package com.jopool.chargingStation.www.controller.api.common;

import com.aliyuncs.exceptions.ClientException;
import com.jopool.chargingStation.www.base.entity.CommonOutsideSnsSendHis;
import com.jopool.chargingStation.www.base.entity.CommonTokenAuthAider;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.base.utils.SendMsgUtil;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.RandomUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Package Name : com.jopool.chargingStation.www.controller.api.common
 * @Author : soupcat
 * @Creation Date : 2017/8/23 下午1:36
 */
@RestController
@RequestMapping("/api/common/sms")
public class ApiCommonSmsController extends BaseController {
    Logger log = org.slf4j.LoggerFactory.getLogger(ApiCommonSmsController.class);
    @Resource
    private CommonService   commonService;
    @Resource
    private PassportService passportService;

    /**
     * JP002001获取手机验证
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4227198
     *
     * @param phone
     * @return
     */
    @RequestMapping("sendSmsCode.htm")
    public Result sendSmsCode(String phone, String type) {
        validateParam(phone, type);
        sendSms(phone, type);
        return new Result(Code.SUCCESS);
    }

    /**
     * JP002002验证手机验证码
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4227397
     *
     * @param phone
     * @return
     */
    @RequestMapping("verifySmsCode.htm")
    public Result verifySmsCode(String phone, String smsCode, String type) {
        validateParam(phone, smsCode, type);
        Result result = new Result(Code.ERROR, "验证码错误");
        boolean isRight = checkSmsCode(phone, smsCode, type);
        if (isRight) {
            result = new Result(Code.SUCCESS);
        }
        return result;
    }

    /**
     * JP002001获取手机验证
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4227198
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("sendSmsCodeToDefaultPhone.htm")
    public Result sendSmsCodeToDefaultPhone(BaseParam baseParam, String type) {
        validateParam(baseParam.getPassportId(), type);
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        sendSms(passport.getPhone(), type);
        return new Result(Code.SUCCESS);
    }

    /**
     * send sms
     *
     * @param phone
     * @param type
     */
    private void sendSms(String phone, String type) {
        validateParam(phone);
        String code;
        boolean sendRltMsg = false;
        //|| ApplicationConfigHelper.getMode() == ModeEnum.SNAPSHOT
        if (ApplicationConfigHelper.getMode() == ModeEnum.RELEASE || ApplicationConfigHelper.getMode() == ModeEnum.SNAPSHOT ) {
            code = RandomUtils.getRandomNum(4);
            try {
                sendRltMsg = SendMsgUtil.sendValideCodeToCarOwner(code, phone);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        } else {
            code = "1234";
        }
        cacheBean.put(Constants.CACHE_KEY_SMS_CODE + type + phone, code, 5, TimeUnit.MINUTES);
        //token
        int outOfDateMin = Integer.parseInt(commonService.getConfigValueByName(Constants.SMS_TOKEN_VALID_MINUTE, "10"));
        CommonTokenAuthAider tokenAuthAider = new CommonTokenAuthAider();
        tokenAuthAider.setId(UUIDUtils.createId());
        tokenAuthAider.setAuthDatetime(new Date());
        tokenAuthAider.setAuthExpiredDatetime(DateUtils.addMinute(tokenAuthAider.getAuthDatetime(), outOfDateMin));
        tokenAuthAider.setAuthSn(code);
        tokenAuthAider.setAuthPurpose(Constants.PURPOSE_REG_VERIFY);
        tokenAuthAider.setSnsNo(phone);
        tokenAuthAider.setSnsType("sms");
        tokenAuthAider.setIsValidToken(false);
        tokenAuthAider.setIsDeleted(false);
        commonService.addTokenAuth(tokenAuthAider);
        //sms his
        CommonOutsideSnsSendHis outsideSnsSendHis = new CommonOutsideSnsSendHis();
        outsideSnsSendHis.setId(UUIDUtils.createId());
        outsideSnsSendHis.setSnsCategory("sms");
        outsideSnsSendHis.setTitle("用户登录验证码");
        outsideSnsSendHis.setContent("发送用户登录验证码");
        outsideSnsSendHis.setReceiverSnsNo(phone);
        outsideSnsSendHis.setSendDatetime(tokenAuthAider.getAuthDatetime());
        outsideSnsSendHis.setSendPurpose(Constants.PURPOSE_REG_VERIFY);
        outsideSnsSendHis.setStatus(sendRltMsg ? "true" : "false");
        outsideSnsSendHis.setStatusResult(sendRltMsg ? "true" : "false");
        outsideSnsSendHis.setIsDeleted(false);
        commonService.addOutsideSnsSendHis(outsideSnsSendHis);
//        String code;
//        if (ApplicationConfigHelper.getMode() == ModeEnum.RELEASE) {
//            code = RandomUtils.getRandomNum(4);
//            Sms sms = new Sms("尊敬的用户，您的验证码为" + code + "，本验证码5分钟内有效，感谢您的使用！", phone);
//            commonService.postMsg(type,code, sms);
//        } else {
//            code = "1234";
//        }
//        int time = Long.parseLong(paramService.getSystemParamValue(SystemParamKeys.SMS_CODE_TIMEOUT));
//        cacheBean.put(Constants.CACHE_KEY_SMS_CODE + type + phone, code, 5, TimeUnit.MINUTES);
    }

}
