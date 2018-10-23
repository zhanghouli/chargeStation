package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.Fence;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.response.DeviceResp;
import com.jopool.chargingStation.www.service.FenceService;
import com.jopool.chargingStation.www.service.GovernmentService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by synn on 2017/10/25.
 */
@RestController
@RequestMapping("/api/fence")
public class ApiFenceController extends BaseController {

    @Resource
    private GovernmentService governmentService;
    @Resource
    private FenceService      fenceService;
    @Resource
    private PassportService   passportService;

    /**
     * 获取围栏
     *
     * @return
     */
    @RequestMapping("/getFences.htm")
    public Result getFences(BaseParam baseParam, String governmentId) {
        List<String> fences = new ArrayList<String>();
        List<Fence> fenceList = fenceService.getByGovernmentId(null, null, governmentId);
        for (Fence fence : fenceList) {
            fences.add(fence.getFencePoints());
        }
        return new Result(Code.SUCCESS, createList(fences));
    }

    /**
     * 获取车辆
     *
     * @return
     */
    @RequestMapping("/getDevices.htm")
    public Result getDevices(BaseParam baseParam, String governmentId) {
        List<DeviceResp> resps = new ArrayList<DeviceResp>();
        List<Carowner> carowners = passportService.getCarownersByGovernmentId(governmentId);
        for (Carowner carowner : carowners) {
            DeviceResp resp = new DeviceResp(carowner);
            resps.add(resp);
        }
        return new Result(Code.SUCCESS, createList(resps));
    }

    /**
     * 获取我的车辆
     *
     * @return
     */
    @RequestMapping("/getMyDevices.htm")
    public Result getMyDevices(BaseParam baseParam) {
        List<DeviceResp> resps = new ArrayList<DeviceResp>();
        Carowner carowner = passportService.getCarownerByPassportId(baseParam.getPassportId());
        DeviceResp resp = new DeviceResp(carowner);
        resps.add(resp);
        return new Result(Code.SUCCESS, createList(resps));
    }

}
