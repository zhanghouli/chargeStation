package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.CommonPicture;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.entity.PassportAccount;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.CommonPictureTypeEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.enums.SmsTypeEnum;
import com.jopool.chargingStation.www.response.PassportAccountLogsResp;
import com.jopool.chargingStation.www.response.PassportBaseInfoResp;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.PageResult;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.controller.api
 * @Author : soupcat
 * @Creation Date : 2017年08月25日 下午1:57
 */
@RestController
@RequestMapping("/api/passport")
public class ApiPassportController extends BaseController {
    @Resource
    private PassportService passportService;
    @Resource
    private CommonService   commonService;

    /**
     * HYD0501基本信息
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864556
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("getDetail.htm")
    public Result getDetail(BaseParam baseParam) {
        validateParam(baseParam.getPassportId());
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        PassportBaseInfoResp resp = new PassportBaseInfoResp(passport);
        PassportAccount passportAccount = passportService.getPassportAmountByPassportId(passport.getId());
        //账户
        if (null != passportAccount) {
            resp.setAmount(passportAccount.getAmount());
        } else {
            passportService.addAccount(passport.getId());
            resp.setAmount(0);
        }
        //头像
        CommonPicture commonPicture = commonService.getPictureByHostId(passport.getId(), CommonPictureTypeEnum.AVATAR.getValue());
        if (null != commonPicture) {
            resp.setAvatar(commonPicture.getRemoteRelativeUrl());
        }
        if (passport.getType().equals(PassportTypeEnum.CAROWNER.getValue())) {
            Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
            if (carowner != null) {
                resp.setHistoryRestTime(carowner.getHistoryRestTime() == null ? 0 : carowner.getHistoryRestTime());
                resp.setDeviceNumber(carowner.getDeviceNumber());
            }
        }
        return new Result(Code.SUCCESS, resp);
    }

    /**
     * HYD0502修改基本信息
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864558
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("modifyDetail.htm")
    public Result modifyDetail(BaseParam baseParam,String deviceNumber, String avatar, String realName, String userName, @RequestParam(defaultValue = "0") int electricBill) {
        validateParam(baseParam.getPassportId());
        passportService.modifyPassportInfo(baseParam.getPassportId(),deviceNumber, avatar, realName, userName, electricBill);
        return new Result(Code.SUCCESS);
    }

    /**
     * HYD0503修改手机号
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864560
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("changePhone.htm")
    public Result changePhone(BaseParam baseParam, String smsCode, String phone) {
        validateParam(baseParam.getPassportId());
        Result result = new Result(Code.ERROR, "验证码错误");
        Passport passport = passportService.getById(baseParam.getPassportId());
        boolean isRight = checkSmsCode(passport.getPhone(), smsCode, SmsTypeEnum.CHANGE_PHONE.getKey());
        if (isRight) {
            passport.setPhone(phone);
            passportService.modify(passport);
            result = new Result(Code.SUCCESS);
        }
        return result;
    }

    /**
     * HYD0504交易记录列表
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864562
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("getAccountLogs.htm")
    public Result getAccountLogs(BaseParam baseParam, String type, String startTime, String endTime, Pagination page) {
        validateParam(baseParam.getPassportId());
        DateParam dateParam = new DateParam();
        getDateParam(startTime, endTime);
        List<PassportAccountLogsResp> passportAccountLogs = passportService.getPassportAmountLogsByPassportId(baseParam.getPassportId(), dateParam, type, page.page());
        return new PageResult(passportAccountLogs, page.getPaginator());
    }

    /**
     * HYD0105更新位置[C][S]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8487074
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("updatePosition.htm")
    public Result updatePosition(BaseParam baseParam, @RequestParam(defaultValue = "0") int lng, @RequestParam(defaultValue = "0") int lat) {
        validateParam(baseParam.getPassportId());
        Carowner carowner = passportService.getCarownerByPassportId(baseParam.getPassportId());
        if (null == carowner) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        return passportService.modifyCarownerPostion(carowner,lng,lat);
    }
}
