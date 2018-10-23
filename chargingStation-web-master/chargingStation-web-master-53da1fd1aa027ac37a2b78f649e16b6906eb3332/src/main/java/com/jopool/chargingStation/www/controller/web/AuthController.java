package com.jopool.chargingStation.www.controller.web;


import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.entity.PassportAuth;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.vo.AuthTreeVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by synn on 2017/9/5.
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Resource
    private CommonService   commonService;
    @Resource
    private PassportService passportService;

    /**
     * 权限 管理
     *
     * @param passportId
     * @return
     */
    @RequestMapping("auth.htm")
    public ModelAndView auth(String passportId, String status) {
        ModelAndView mv = getSessionUserMV("auth/auth");
        mv.addObject("passportId", passportId)
                .addObject("status", status);
        return mv;
    }

    /**
     * 获取 所有 权限
     *
     * @param passportId
     * @return
     */
    @RequestMapping("getAuthPassport.htm")
    public List<AuthTreeVo> getAuthPassport(String passportId) {
        validateParam(passportId);
        List<AuthTreeVo> tree = commonService.getAuthTreeByPassportId(getSessionUser().getPassportId(), passportId);
        return tree;
    }

    /**
     * 权限 修改
     *
     * @param passportId
     * @param auth
     * @return
     */
    @RequestMapping("doSavePassportAuth.htm")
    public Result doSavePassportAuth(String passportId, String auth) {
        validateParam(passportId, auth);
        Passport passport = passportService.getById(passportId);
        List<PassportAuth> passportAuthList = commonService.searchPassportAuths(passportId);
        for (PassportAuth passportAuth : passportAuthList) {
            commonService.removePassportAuths(passportAuth.getId());
        }
        String[] auths = auth.split(",");
        for (String a : Arrays.asList(auths)) {
            PassportAuth passportAuth = new PassportAuth();
            passportAuth.setId(UUIDUtils.createId());
            passportAuth.setPassportId(passportId);
            passportAuth.setAuthType(passport.getType());
            passportAuth.setAuthId(a);
            commonService.addPassportAuth(passportAuth);
        }
        return new Result(Code.SUCCESS);
    }
}
