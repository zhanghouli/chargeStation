package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.MessageTemplate;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.entity.PassportAccount;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.MessageTemplateEnum;
import com.jopool.chargingStation.www.enums.PassportAccountLogTypeEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.response.AppPushMessage;
import com.jopool.chargingStation.www.response.FinancialResp;
import com.jopool.chargingStation.www.service.AccountService;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.WxPushService;
import com.jopool.chargingStation.www.vo.AccountOpetatorOrEstate;
import com.jopool.chargingStation.www.vo.AccountVo;
import com.jopool.chargingStation.www.vo.PassportAccountLogVo;
import com.jopool.chargingStation.www.vo.WxPushVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jopool.jweb.utils.DateUtils.date2StringByDay;
import static com.jopool.jweb.utils.DateUtils.getMaxDayOfMonth;

/**
 * Created by synn on 2017/9/7.
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Resource
    private AccountService accountService;

    @Resource
    private PassportService passportService;
    @Resource
    private CommonService   commonService;
    @Resource
    private WxPushService   wxPushService;

    /**
     * 所有列表
     *
     * @param timeStartStr
     * @param timeEndStr
     * @param searchBaseVo
     * @param page
     * @return
     */
    @RequestMapping("accountRecharge.htm")
    public ModelAndView accountRecharge(String timeStartStr, String timeEndStr, SearchBaseVo searchBaseVo, Pagination page) throws ParseException {
        DateParam dateParam = new DateParam(timeStartStr, timeEndStr);
        Integer sum = accountService.passportAccountLogSum(dateParam, PassportAccountLogTypeEnum.RECHARGE.getValue());
        List<PassportAccountLogVo> passportAccountLogVos = accountService.listPassportAccountLog(dateParam, PassportAccountLogTypeEnum.RECHARGE.getValue(), searchBaseVo, page.page());
        ModelAndView mv = getPageMv("account/accountRecharge", passportAccountLogVos, page);
        return mv.addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr)
                .addObject("sum", sum)
                .addObject("payType", PassportAccountLogTypeEnum.RECHARGE.getValue());
    }

    /**
     * 消费记录
     *
     * @param page
     * @return
     */
    @RequestMapping("accountPay.htm")
    public ModelAndView accountPay(String timeStartStr, String timeEndStr, SearchBaseVo searchBaseVo, Pagination page) throws ParseException {
        DateParam dateParam = new DateParam(timeStartStr, timeEndStr);
        Integer sum = accountService.passportAccountLogSum(dateParam, PassportAccountLogTypeEnum.PAY.getValue());
        List<PassportAccountLogVo> passportAccountLogVos = accountService.listPassportAccountLog(dateParam, PassportAccountLogTypeEnum.PAY.getValue(), searchBaseVo, page.page());
        ModelAndView mv = getPageMv("account/accountPay", passportAccountLogVos, page);
        return mv.addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr)
                .addObject("sum", sum)
                .addObject("payType", PassportAccountLogTypeEnum.PAY.getValue());
    }

    /**
     * 财务管理
     *
     * @return
     */
    @RequestMapping("financialInfo.htm")
    public ModelAndView financialInfo(AccountVo accountVo) {
        //日期
        formatDay(accountVo);
        DateParam dateParam = new DateParam();
        try {
            dateParam = new DateParam(accountVo.getTimeStartStr(), accountVo.getTimeEndStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FinancialResp resp = accountService.getFinancialInfo(dateParam, Pagination.nullPage());
        return new ModelAndView("account/financialInfo")
                .addObject("financial", resp)
                .addObject("accountVo", accountVo);
    }

    /**
     * 运营商报表
     *
     * @return
     */
    @RequestMapping("financialOperator.htm")
    public ModelAndView financialOperator(AccountVo accountVo, Pagination page) {
        //日期
        formatDay(accountVo);
        DateParam dateParam = new DateParam();
        try {
            dateParam = new DateParam(accountVo.getTimeStartStr(), accountVo.getTimeEndStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<AccountOpetatorOrEstate> accountOpetatorOrEstates = accountService.getOperatorAccount(dateParam, accountVo.getKeyword(), PassportTypeEnum.OPERATOR.getValue(), page);
        ModelAndView mv = getPageMv("account/financialOperator", accountOpetatorOrEstates, page);
        return mv.addObject("accountVo", accountVo)
                .addObject("accountOpetatorOrEstates", accountOpetatorOrEstates);
    }

    /**
     * 物业管理
     *
     * @return
     */
    @RequestMapping("financialEstate.htm")
    public ModelAndView financialEstate(AccountVo accountVo, Pagination page) {
        //日期
        formatDay(accountVo);
        DateParam dateParam = new DateParam();
        try {
            dateParam = new DateParam(accountVo.getTimeStartStr(), accountVo.getTimeEndStr());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<AccountOpetatorOrEstate> accountOpetatorOrEstates = accountService.getEstateAccount(dateParam, accountVo.getKeyword(), PassportTypeEnum.ESTATE.getValue(), page);
        ModelAndView mv = getPageMv("account/financialEstate", accountOpetatorOrEstates, page);
        return mv.addObject("accountVo", accountVo)
                .addObject("accountOpetatorOrEstates", accountOpetatorOrEstates);
    }

    /**
     * 账户充值
     *
     * @param phone
     * @return
     */
    @RequestMapping("addAccountCarOwner.htm")
    public Result addAccountCarOwner(String phone, int amount) {
        Passport passport = passportService.getByPhoneAndType(phone, PassportTypeEnum.CAROWNER.getValue());
        AppPushMessage appPushMessage = new AppPushMessage("金额充值通知", "充值详情", "http://h5.h1d.com.cn/station/index.html#/member/walletRecord");
        if (passport == null) {
            return new Result(Code.ERROR, CodeMessage.CAROWNER_NOT_EXIST);
        }
        accountService.modifyPassportAccount(passport.getId(), amount, PassportAccountLogTypeEnum.RECHARGE.getValue(), getSessionUser().getPassportId(), "后台充值");
        PassportAccount passportAccount = passportService.getPassportAmountByPassportId(passport.getId());
        //推送凭证
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.RECHARGE_AMOUNT.getValue());
        Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
        if (messageTemplate != null && tokenResp != null) {
            wxPushService.pushRechargeAmount(new WxPushVo(tokenResp, messageTemplate, carowner), amount, passportAccount);
        }
        //个推
        if (!StringUtils.isEmpty(carowner.getClientId())) {
            appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 车主 查找
     *
     * @param phone
     * @param type
     * @return
     */
    @RequestMapping("getByCarOwnerPhone.htm")
    public Result getByCarOwnerPhone(String phone, String type) {
        return new Result(Code.SUCCESS, passportService.getByPhoneAndType(phone, type));
    }

    /**
     * 获取日期
     *
     * @param accountVo
     */
    public void formatDay(AccountVo accountVo) {
        if (StringUtils.isEmpty(accountVo.getTimeStartStr()) && StringUtils.isEmpty(accountVo.getTimeEndStr())) {
            if ("yesterday".equals(accountVo.getDay())) {
                accountVo.setTimeStartStr(date2StringByDay(DateUtils.addDay(new Date(), -1)));
                accountVo.setTimeEndStr(date2StringByDay(DateUtils.addDay(new Date(), -1)));
            } else if ("today".equals(accountVo.getDay())) {
                accountVo.setTimeStartStr(date2StringByDay(new Date()));
                accountVo.setTimeEndStr(date2StringByDay(new Date()));
            } else if ("week".equals(accountVo.getDay())) {
                accountVo.setTimeStartStr(date2StringByDay(DateUtils.getDateOfCurrentWeek(1)));
                accountVo.setTimeEndStr(date2StringByDay(DateUtils.getDateOfCurrentWeek(7)));
            } else if ("month".equals(accountVo.getDay())) {
                accountVo.setTimeStartStr(DateUtils.getStartDayOfThisMonth());
                accountVo.setTimeEndStr(lastMonthDay());
            }
        } else {
            accountVo.setTimeStartStr(date2StringByDay(DateUtils.getStartDate(DateUtils.string2Date(accountVo.getTimeStartStr(), "yyyy-MM-dd"))));
            accountVo.setTimeEndStr(date2StringByDay(DateUtils.getEndDate(DateUtils.string2Date(accountVo.getTimeEndStr(), "yyyy-MM-dd"))));
        }


    }

    public static String lastMonthDay() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, getMaxDayOfMonth(date.getYear(), date.getMonth()));
        return date2StringByDay(c.getTime());
    }
}
