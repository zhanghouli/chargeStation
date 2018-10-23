package com.jopool.chargingStation.www.controller.web;


import com.jopool.chargingStation.www.base.entity.AppVersion;
import com.jopool.chargingStation.www.base.entity.CommonArea;
import com.jopool.chargingStation.www.base.entity.CommonSystemConfig;
import com.jopool.chargingStation.www.base.entity.OpenArea;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.AppVersionService;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.SystemConfigService;
import com.jopool.chargingStation.www.vo.OpenAreaVo;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/8/31.
 */
@RestController
@RequestMapping("/system")
public class SystemConfigController extends BaseController {

    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private CommonService       commonService;
    @Resource
    private AppVersionService   appVersionService;

    /**
     * 系统 参数 列表
     *
     * @return
     */
    @RequestMapping("systemConfigList.htm")
    public ModelAndView systemConfigList(SearchBaseVo searchBaseVo, Pagination page) {
        List<CommonSystemConfig> commonSystemConfigs = systemConfigService.searchBaseVo(searchBaseVo, page.page());
        ModelAndView mv = getPageMv("system/systemConfigList", commonSystemConfigs, page);
        return mv.addObject("keyword", searchBaseVo.getKeyword());
    }

    /**
     * 开通城市
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    @RequestMapping("openAreaList.htm")
    public ModelAndView openArea(SearchBaseVo searchBaseVo, Pagination page) {
        List<OpenAreaVo> openAreas = systemConfigService.searchBaseVoOpenArea(searchBaseVo, page.page());
        ModelAndView mv = getPageMv("system/openAreaList", openAreas, page);
        return mv.addObject("keyword", searchBaseVo.getKeyword());
    }


    /**
     * 添加 修改 系统 参数
     *
     * @param systemConfigReq
     * @return
     */
    @RequestMapping("addOrModifyConfig.htm")
    public Result addOrModifyConfig(CommonSystemConfig systemConfigReq) {

        if (!StringUtils.isEmpty(systemConfigReq.getId())) {
            CommonSystemConfig commonSystemConfig = systemConfigService.getById(systemConfigReq.getId());
            if (commonSystemConfig == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            commonSystemConfig.setName(systemConfigReq.getName());
            commonSystemConfig.setVal(systemConfigReq.getVal());
            commonSystemConfig.setRemark(systemConfigReq.getRemark());
            commonSystemConfig.setModifyTime(new Date());
            systemConfigService.modifyCommonSystemConfig(commonSystemConfig);
        } else {
            CommonSystemConfig commonSystemConfig = new CommonSystemConfig();
            commonSystemConfig.setId(UUIDUtils.createId());
            commonSystemConfig.setName(systemConfigReq.getName());
            commonSystemConfig.setVal(systemConfigReq.getVal());
            commonSystemConfig.setRemark(systemConfigReq.getRemark());
            commonSystemConfig.setCreator(getSessionUser().getPassportId());
            commonSystemConfig.setCreationTime(new Date());
            commonSystemConfig.setIsDeleted(false);
            systemConfigService.addCommonSystemConfig(commonSystemConfig);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 开通 城市 添加 修改
     *
     * @param province
     * @param city
     * @param id
     * @return
     */
    @RequestMapping("addOrModifyOpenArea.htm")
    public Result addOrModifyOpenArea(String province, String city, String id) {
        if (!StringUtils.isEmpty(id)) {
            OpenArea openArea = systemConfigService.getByOpenAreaId(id);
            if (openArea == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            CommonArea commonArea = commonService.getByCommonAreaCode(city);

            if (commonArea == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            OpenArea openAreaOld = commonService.getAreaId(commonArea.getId());
            if (!openAreaOld.getAreaId().equals(openArea.getAreaId()) && openAreaOld != null) {
                return new Result(Code.ERROR, "此城市已经开通");
            }
            openArea.setAreaId(commonArea.getId());

            openArea.setModifier(getSessionUser().getPassportId());
            openArea.setModifyTime(new Date());
            systemConfigService.modifyArea(openArea);
        } else {
            OpenArea openArea = new OpenArea();
            //code  查找开通城市
            CommonArea commonArea = commonService.getByCommonAreaCode(city);
            openArea.setId(UUIDUtils.createId());
            if (commonArea == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            OpenArea openAreaOld = commonService.getAreaId(commonArea.getId());
            if (openAreaOld != null) {
                return new Result(Code.ERROR, "此城市已经开通");
            }
            openArea.setAreaId(commonArea.getId());

            openArea.setIsEnabled(true);
            openArea.setIsDeleted(false);
            openArea.setCreator(getSessionUser().getPassportId());
            openArea.setCreationTime(new Date());
            systemConfigService.addOpenArea(openArea);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 系统 参数 详情
     *
     * @param configId
     * @return
     */
    @RequestMapping("getConfig.htm")
    public Result getConfig(String configId) {
        validateParam(configId);
        CommonSystemConfig commonSystemConfig = systemConfigService.getById(configId);
        return new Result(Code.SUCCESS, commonSystemConfig);
    }

    /**
     * 开通城市 详情
     *
     * @param openAreaId
     * @return
     */
    @RequestMapping("getOpenArea.htm")
    public Result getOpenArea(String openAreaId) {
        validateParam(openAreaId);
        OpenArea openArea = systemConfigService.getByOpenAreaId(openAreaId);
        CommonArea commonArea = commonService.getByCommonAreaId(openArea.getAreaId());
        String provinceCode = "";
        String cityCode = "";
        if (commonArea != null) {
            String[] location = commonArea.getLocation().split(",");
            provinceCode = location[0];
            cityCode = location[1];
        }
        OpenAreaVo openAreaVo = new OpenAreaVo(openArea, provinceCode, cityCode);
        return new Result(Code.SUCCESS, openAreaVo);
    }


    /**
     * 删除 系统 参数
     *
     * @param configId
     * @return
     */
    @RequestMapping("removeConfigId.htm")
    public Result removeConfigId(String configId) {
        validateParam(configId);
        return systemConfigService.removeSystemConfigId(configId);
    }

    /**
     * 开通 城市 删除
     *
     * @param openAreaId
     * @return
     */
    @RequestMapping("removeOpenArea.htm")
    public Result removeOpenArea(String openAreaId) {
        validateParam(openAreaId);
        return systemConfigService.removeOpenArea(openAreaId);
    }

    /**
     * App 更新 配置
     */
    @RequestMapping("appVersionList.htm")
    public ModelAndView appVersionList(Pagination pagination) {
        List<AppVersion> appVersions = appVersionService.getAll(pagination.page());
        ModelAndView mv = getPageMv("system/appVersionList", appVersions, pagination);
        return mv;
    }

    /**
     * 新增 修改 APP 参数
     *
     * @param appVersion
     * @return
     */
    @RequestMapping("addOrModifyAppVersion.htm")
    public Result addOrModifyAppVersion(AppVersion appVersion) {
        if (!StringUtils.isEmpty(appVersion.getId())) {
            AppVersion appVersionOld = appVersionService.getById(appVersion.getId());
            if (appVersionOld == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            appVersionOld.setAppId(appVersion.getAppId());
            appVersionOld.setOs(appVersion.getOs());
            appVersionOld.setLastVersion(appVersion.getLastVersion());
            appVersionOld.setUrl(appVersion.getUrl());
            appVersionOld.setDescription(appVersion.getDescription());
            appVersionOld.setIsForceUpdate(appVersion.getIsForceUpdate());
            appVersionOld.setCreationTime(new Date());
            appVersionOld.setCreator(getSessionUser().getPassportId());
            appVersionService.modify(appVersionOld);
        } else {
            appVersion.setId(UUIDUtils.createId());
            appVersion.setCreationTime(new Date());
            appVersion.setCreator(getSessionUser().getPassportId());
            appVersion.setIsDeleted(false);
            appVersionService.add(appVersion);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 查看修改
     *
     * @param appVersionId
     * @return
     */
    @RequestMapping("changeAppVersion.htm")
    public Result changeAppVersion(String appVersionId) {
        validateParam(appVersionId);
        AppVersion appVersion = appVersionService.getById(appVersionId);
        return new Result(Code.SUCCESS, appVersion);
    }

    /**
     * 删除
     *
     * @param appVersionId
     * @return
     */
    @RequestMapping("removeAppVersion.htm")
    public Result removeAppVersion(String appVersionId) {
        validateParam(appVersionId);
        appVersionService.removeById(appVersionId);
        return new Result(Code.SUCCESS);
    }
}
