package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.PasswordHash;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.controller.api
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午5:33
 */
@RestController
@RequestMapping("/api/operator")
public class ApiOperatorController extends BaseController {
    @Resource
    private PassportService passportService;


    /**
     * HYD0202修改密码
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864592
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("changePassword.htm")
    public Result changePassword(BaseParam baseParam, String oldPassword, String newPassword) {
        validateParam(oldPassword, newPassword);
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (passport == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (!PasswordHash.validatePassword(oldPassword, passport.getPassword())) {
            return new Result(Code.ERROR, CodeMessage.OLD_PWD_ERROR);
        }
        if (!StringUtils.isEmpty(newPassword)) {
            passport.setPassword(PasswordHash.createHash(newPassword, UUIDUtils.createId()));
        }
        passportService.modify(passport);
        return new Result(Code.SUCCESS);
    }

}
