package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.Fence;
import com.jopool.chargingStation.www.base.entity.Government;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.response.DeviceResp;
import com.jopool.chargingStation.www.service.FenceService;
import com.jopool.chargingStation.www.service.GovernmentService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.vo.FenceTreeVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/10/25.
 */
@RestController
@RequestMapping("/fence")
public class FenceController extends BaseController {

    @Resource
    private GovernmentService governmentService;
    @Resource
    private FenceService      fenceService;
    @Resource
    private PassportService   passportService;


    /**
     * 电子围栏
     *
     * @return
     */
    @RequestMapping("/fenceList.htm")
    public ModelAndView fenceList(Fence fence) {
        //政府人员登录
        if (PassportTypeEnum.GOVERNMENT.getValue().equals(getSessionUser().getPassportType())) {
            Government government = governmentService.getByPassportId(getSessionUser().getPassportId());
            if (government != null) {
                fence.setGovernmentId(government.getId());
            }
        }
        return getMV("/fence/fenceList")
                .addObject("fence", fence);
    }

    /**
     * 树
     *
     * @param fence
     * @return
     */
    @RequestMapping("/getCategoryTree.htm")
    public List<FenceTreeVo> getCategoryTree(Fence fence) {
        return fenceService.getByGovernmentIdTree(fence);
    }

    /**
     * 电子围栏添加
     *
     * @param fence
     * @return
     */
    @RequestMapping("/addOrModifyFence.htm")
    public Result addFence(Fence fence) {
        if (!StringUtils.isEmpty(fence.getId())) {
            //修改
            Fence fenceOld = fenceService.searchFenceId(fence.getId());
            if (fenceOld == null) {
                return new Result(Code.ERROR, "数据异常");
            }
            //名称 重复判断
            List<Fence> fenceName = fenceService.getByGovernmentId("", fence.getName(), fence.getGovernmentId());
            if (fenceName.size() > 0 && !fenceOld.getName().equals(fence.getName())) {
                return new Result(Code.ERROR, "此围栏名称已存在");
            }
            fenceOld.setName(fence.getName());
            fenceOld.setFencePoints(fence.getFencePoints());
            fenceOld.setModifier(getSessionUser().getPassportId());
            fenceOld.setModifyTime(new Date());
            fenceService.modifyGovernmentFence(fenceOld);

        } else {
            //名称 重复判断
            List<Fence> fenceName = fenceService.getByGovernmentId("", fence.getName(), fence.getGovernmentId());
            if (fenceName.size() > 0) {
                return new Result(Code.ERROR, "此围栏名称已存在");
            }
            fence.setCreator(getSessionUser().getPassportId());
            fence.setIsEnabled(true);
            fenceService.addGovernmentFence(fence);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 围栏 删除
     *
     * @param fence
     * @return
     */
    @RequestMapping("/doRemoveId.htm")
    public Result doRemoveId(Fence fence) {
        validateParam(fence.getId());
        fenceService.removeGovernmentFence(fence.getId());
        return new Result(Code.SUCCESS);
    }

    /**
     * 设备位置
     *
     * @return
     */
    @RequestMapping("/deviceList.htm")
    public ModelAndView deviceList() {
        return getMV("/fence/deviceList");
    }

    /**
     * 获取围栏
     *
     * @return
     */
    @RequestMapping("/getFences.htm")
    public Result getFences(String governmentId) {
        List<String> fences = new ArrayList<String>();
        if (StringUtils.isEmpty(governmentId)) {
            Government government = governmentService.getByPassportId(getSessionUser().getUserId());
            if (null == government) {
                return new Result(Code.ERROR, "数据错误");
            }
            governmentId = government.getId();
        }
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
    public Result getDevices(String governmentId) {
        List<DeviceResp> resps = new ArrayList<DeviceResp>();
        List<Carowner> carowners = passportService.getCarownersByGovernmentId(governmentId);
        for (Carowner carowner : carowners) {
            DeviceResp resp = new DeviceResp(carowner);
            resps.add(resp);
        }
        return new Result(Code.SUCCESS, createList(resps));
    }

}
