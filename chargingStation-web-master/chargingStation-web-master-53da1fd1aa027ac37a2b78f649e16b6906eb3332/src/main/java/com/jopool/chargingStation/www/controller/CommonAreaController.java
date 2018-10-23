package com.jopool.chargingStation.www.controller;

import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by synn on 2017/9/4.
 */
@RestController
@RequestMapping("/area")
public class CommonAreaController {

    @Resource
    private CommonService commonService;

    @RequestMapping("getAreaJson.htm")
    public Result getAreaJson(HttpServletRequest request) {
        Map<String, Map<String, String>> mapMap = new HashMap<String, Map<String, String>>();
        mapMap = (Map<String, Map<String, String>>) request.getSession().getAttribute("mapArea");
        if (mapMap == null) {
            mapMap = commonService.getArea();
            request.getSession().setAttribute("mapArea",mapMap);
        }
        return new Result(Code.SUCCESS, mapMap);
    }
}
