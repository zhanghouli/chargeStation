package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.AppVersion;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.SystemStatusEnum;
import com.jopool.chargingStation.www.service.AppVersionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by synn on 2017/12/11.
 */
@Controller
@RequestMapping("/app/")
public class AppDownloadController extends BaseController {
    @Resource
    private AppVersionService appVersionService;

    /**
     * 下载页面
     *
     * @return
     */
    @RequestMapping("dl.htm")
    public ModelAndView dl(String appId) {
        validateParam(appId);
        AppVersion IOS = appVersionService.getByAppIdAndOS(appId, SystemStatusEnum.IOS.getName());
        AppVersion ANDROID = appVersionService.getByAppIdAndOS(appId, SystemStatusEnum.ANDROID.getName());
        return new ModelAndView("app/downIphone")
                .addObject("ios",IOS)
                .addObject("android",ANDROID);
    }
}
