package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.PassportStatusEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.service.CmppProxyService;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.vo.PassportAuthVo;
import com.jopool.chargingStation.www.vo.SessionUser;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.PasswordHash;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private PassportService  passportService;
    @Resource
    private CommonService    commonService;
    @Resource
    private CmppProxyService cmppProxyService;


    /**
     * login
     *
     * @param model
     * @return
     */
    @RequestMapping("login.htm")
    public ModelAndView login(RedirectAttributesModelMap model) {
        return getSessionUserMV("login", model);
    }

    /**
     * 登录
     *
     * @param request
     * @param userName
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("doLogin.htm")
    public ModelAndView doLogin(HttpServletRequest request, String userName, String password, RedirectAttributesModelMap model) {
        log.debug("userName:{},password:{}", userName, password);
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            addErrorAction(model, "请输入账号或密码");
            return new ModelAndView("redirect:login.htm");
        }

        List<Passport> canLoginList = new ArrayList<Passport>();
        //可登录账号
        List<Passport> passportList = passportService.getByUserNameAndTypes(userName, PassportStatusEnum.NORMAL.getValue(), new String[]{PassportTypeEnum.ADMIN.getValue(), PassportTypeEnum.GOVERNMENT.getValue()});
        for (Passport passport : passportList) {
            if (!PassportStatusEnum.NORMAL.getValue().equals(passport.getStatus())) {
                continue;
            }
//            if (PassportTypeEnum.ADMIN.getValue() == passport.getType()) {
//                Shop shop = shopService.getById(passport.getShopId());
//                if (null == shop || shop.getIsDeleted()) {
//                    continue;
//                }
//            }
            if (!StringUtils.isEmpty(passport.getPassword()) && !PasswordHash.validatePassword(password, passport.getPassword())) {
                continue;
            } else {
                canLoginList.add(passport);
            }
        }
        //
        if (Validators.isEmpty(canLoginList)) {
            addErrorAction(model, "账号或密码错误");
            return new ModelAndView("redirect:login.htm");
        } else {

            Passport passport = canLoginList.get(0);
            //用户登录信息设置
            SessionUser user = new SessionUser();
            user.setPassportId(passport.getId());
            user.setPassportType(passport.getType());
            user.setName(passport.getUserName());
            user.setUserId(passport.getId());
            //用户权限设置
            Map<PassportAuthVo, List<PassportAuthVo>> auth = commonService.getAuthByPassportId(user.getPassportId());
            user.setAuth(auth);
            //保存
            request.getSession().setAttribute(Constants.SESSION_KEY_LOGIN_USER, user);
            //登录日志
            //passportService.addLoginLog(passport.getShopId(), passport.getId(), user.getType(), TerminalTypeEnum.WEB.getName(), IPUtils.getIP(request));
            //
            return new ModelAndView("redirect:index.htm");
        }
    }


    /**
     * 退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("logout.htm")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SESSION_KEY_LOGIN_USER);
        return new ModelAndView("redirect:login.htm");
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("index.htm")
    public ModelAndView index() {
        return getSessionUserMV("index");
    }


    /**
     * platform首页
     *
     * @return
     */
    @RequestMapping("pm_index.htm")
    public ModelAndView pm_index() {
        return getSessionUserMV("pm_index");
    }

    /**
     * 顶部
     *
     * @return
     */
    @RequestMapping("top.htm")
    public ModelAndView top() {
        return getSessionUserMV("/top");
    }

    /**
     * 左侧
     *
     * @return
     */
    @RequestMapping("left.htm")
    public ModelAndView left() {
        ModelAndView modelAndView = getSessionUserMV("/left");
        modelAndView.addObject("auth", getSessionUser().getAuth());
        return modelAndView;
    }

    /**
     * 物联网卡短信测试
     *
     * @return
     */
    @RequestMapping("cmpp.htm")
    public Result cmpp(String phone, String content) {
        validateOrParam(phone, content);
        boolean r = cmppProxyService.send(phone, content);
        return new Result(r ? Code.SUCCESS : Code.ERROR);
    }

}
