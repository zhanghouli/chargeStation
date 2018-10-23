package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.base.entity.RechargePackage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.response.AccountDetailResp;
import com.jopool.chargingStation.www.service.AccountService;
import com.jopool.chargingStation.www.service.ConsumePackageService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.RechargePackageService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.controller.api
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午5:29
 */
@RestController
@RequestMapping("/api/account")
public class ApiAccountController extends BaseController {
    @Resource
    private RechargePackageService rechargePackageService;
    @Resource
    private AccountService         accountService;
    @Resource
    private PassportService        passportService;

    /**
     * HYD0103在线充值
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864748
     *
     * @param rechargePackageId
     * @param amount
     * @return
     */
    @RequestMapping("recharge.htm")
    public Result recharge(BaseParam baseParam, String rechargePackageId, @RequestParam(defaultValue = "0") int amount) {
        if (amount <= 0) {
            return new Result(Code.ERROR, "请选择充值套餐或者选择金额");
        } else {
            int rechargeAmount = 0;
            if (!StringUtils.isEmpty(rechargePackageId)) {
                RechargePackage rechargePackage = rechargePackageService.getById(rechargePackageId);
                rechargeAmount = rechargePackage.getPayment();
            } else if (amount > 0) {
                rechargeAmount = amount;
            }
            return passportService.recharge(baseParam.getPassportId(), rechargePackageId, rechargeAmount);
        }
    }

    /**
     * HYD0203收入信息
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864594
     *
     * @return
     */
    @RequestMapping("getAccountDetail.htm")
    public Result getAccountDetail(BaseParam baseParam) {
        validateParam(baseParam.getPassportId());
        AccountDetailResp resp = accountService.getAccountDetail(baseParam.getPassportId());
        return new Result(Code.SUCCESS, resp);
    }
}
