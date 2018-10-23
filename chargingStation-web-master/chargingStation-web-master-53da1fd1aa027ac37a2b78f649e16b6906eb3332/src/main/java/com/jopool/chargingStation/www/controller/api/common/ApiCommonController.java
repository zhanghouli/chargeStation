package com.jopool.chargingStation.www.controller.api.common;


import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.request.CheckVersionReq;
import com.jopool.chargingStation.www.response.ConsumePackageResp;
import com.jopool.chargingStation.www.response.OpenAreaResp;
import com.jopool.chargingStation.www.response.RechargePackageResp;
import com.jopool.chargingStation.www.response.common.CheckVersionResp;
import com.jopool.chargingStation.www.service.AppVersionService;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.ConsumePackageService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Package Name : com.jopool.chargingStation.www.controller.api.common
 * @Author : soupcat
 * @Creation Date : 2017/8/23 下午1:36
 */
@RestController
@RequestMapping("/api/common/")
public class ApiCommonController extends BaseController {
    Logger log = org.slf4j.LoggerFactory.getLogger(ApiCommonController.class);
    @Resource
    private CommonService         commonService;
    @Resource
    private PassportService       passportService;
    @Resource
    private ConsumePackageService consumePackageService;
    @Resource
    private AppVersionService     appVersionService;

    /**
     * HYD0201运营商登录
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864629
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("login.htm")
    public Result login(String userName, String password, HttpServletRequest request) {
        validateParam(userName, password);
        return passportService.login(userName, password, request);
    }

    /**
     * HYD0401开通城市列表
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864538
     *
     * @return
     */
    @RequestMapping("getOpenAreas.htm")
    public Result getOpenAreas() {
        List<OpenAreaResp> resps = commonService.getOpenArea();
        return new Result(Code.SUCCESS, resps);
    }

    /**
     * HYD0402意见反馈
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864579
     *
     * @param baseParam
     * @param content
     * @param pics
     * @return
     */
    @RequestMapping("addFeedback.htm")
    public Result addFeedback(BaseParam baseParam, String content, String pics) {
        validateParam(baseParam.getPassportId());
        return commonService.addFeedback(baseParam.getPassportId(), content, pics);
    }

    /**
     * HYD0403充值套餐
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864753
     *
     * @return
     */
    @RequestMapping("getRechargePackages.htm")
    public Result getRechargePackages() {
        List<RechargePackageResp> resps = commonService.getRechargePackages();
        return new Result(Code.SUCCESS, resps);
    }

    /**
     * HYD0312套餐名称[O][S]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8192054
     *
     * @return
     */
    @RequestMapping("getConsumePackageDetail.htm")
    public Result getConsumePackageDetail(String consumePackageId) {
        validateParam(consumePackageId);
        ConsumePackageResp consumePackage = consumePackageService.getConsumePackageDetail(consumePackageId);
        return new Result(Code.SUCCESS, consumePackage);
    }

    /**
     * HYD0313套餐列表[O][S]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8192060
     *
     * @return
     */
    @RequestMapping("getConsumePackageList.htm")
    public Result getConsumePackageList(BaseParam baseParam) {
        validateParam(baseParam.getPassportId());
        Operator operator = passportService.getOperatorByPassportId(baseParam.getPassportId());
        if (null == operator) {
            return new Result(Code.ERROR, "用户不存在或者无权操作");
        }
        List<ConsumePackage> consumePackageList = consumePackageService.getConsumePackageList(operator.getId());
        return new Result(Code.SUCCESS, consumePackageList);
    }

    /**
     * HYD0315按量充值配置列表[O][S]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8487082
     *
     * @return
     */
    @RequestMapping("getChargeCostPackageList.htm")
    public Result getChargeCostPackageList(BaseParam baseParam) {
        validateParam(baseParam.getPassportId());
        List<ChargeCostPackage> consumePackageList = consumePackageService.getChargeCostPackage();
        return new Result(Code.SUCCESS, createList(consumePackageList));
    }

    /**
     * JP004001新版本检查
     * http://wiki.jopool.net/pages/viewpage.action?pageId=4227210
     *
     * @param checkVersionReq
     * @return
     */
    @RequestMapping("checkVersion.htm")
    public Result checkVersion(BaseParam baseParam, CheckVersionReq checkVersionReq) {
        validateParam(checkVersionReq.getVersion(), checkVersionReq.getOs());
        AppVersion appVersion = appVersionService.getByAppIdAndOS(checkVersionReq.getAppId(), checkVersionReq.getOs());
        if (null == appVersion) {
            return new Result(Code.ERROR, "");
        }
        CheckVersionResp resp = new CheckVersionResp();
        resp.setLastVersion(appVersion.getLastVersion());
        resp.setUrl(appVersion.getUrl());
        resp.setForceUpdate(appVersion.getIsForceUpdate());
        resp.setDescription(appVersion.getDescription());
        return new Result(Code.SUCCESS, resp);
    }

    /**
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8683678
     *
     * @param baseParam
     * @param clientId
     * @param isOnline
     * @return
     */
    @RequestMapping("getCarownerClientId.htm")
    public Result getCarownerClientId(BaseParam baseParam, String clientId, boolean isOnline) {
        validateParam(baseParam.getPassportId());
        Carowner carowner = passportService.getCarownerByPassportId(baseParam.getPassportId());
        if (carowner == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (isOnline) {
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                if (!Arrays.asList(carowner.getClientId().split(",", -1)).contains(clientId)) {
                    carowner.setClientId(carowner.getClientId() + "," + clientId);
                }
            } else {
                carowner.setClientId(clientId);
            }
        } else {
            //遍历返回新的
            List<String> clientIds = new ArrayList<>();
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                Iterator<String> iterable = Arrays.asList(carowner.getClientId().split(",", -1)).iterator();
                while (iterable.hasNext()) {
                    String s = iterable.next();
                    if (s.equals(clientId)) {
                        continue;
                    }
                    clientIds.add(s);
                }
            }
            carowner.setClientId(StringUtils.join(clientIds.toArray(), ","));
        }
        passportService.modifyCarOwner(carowner);
        return new Result(Code.SUCCESS);
    }
}
