package com.jopool.chargingStation.www.controller.api;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.utils.LBSUtils;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.request.ModifyStationPortReq;
import com.jopool.chargingStation.www.request.ModifyStationReq;
import com.jopool.chargingStation.www.request.ReportFaultReq;
import com.jopool.chargingStation.www.request.StationReq;
import com.jopool.chargingStation.www.response.StationPortResp;
import com.jopool.chargingStation.www.response.StationResp;
import com.jopool.chargingStation.www.service.ConsumePackageService;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.StationService;
import com.jopool.chargingStation.www.vo.WarningVo;
import com.jopool.jweb.entity.BaseParam;
import com.jopool.jweb.entity.PageResult;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.redis.RedisProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.controller.api
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午5:38
 */
@RestController
@RequestMapping("/api/station")
public class ApiStationController extends BaseController {
    @Resource
    private StationService        stationService;
    @Resource
    private OrderService          orderService;
    @Resource
    private PassportService       passportService;
    @Resource
    private RedisProvider         redisProvider;
    @Resource
    private ConsumePackageService consumePackageService;

    /**
     * HYD0301附近充电站列表
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864540
     *
     * @param baseParam
     * @param req
     * @param page
     * @return
     */
    @RequestMapping("getStations.htm")
    public Result getStations(BaseParam baseParam, StationReq req, @RequestParam(defaultValue = "0") int page) {
        List<StationResp> resps = stationService.getNearByStations(req, page);
        return new Result(Code.SUCCESS, createList(resps));
    }

    /**
     * HYD0301.1用户使用中的电站[C][S]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8487165
     *
     * @param baseParam
     * @return
     */
    @RequestMapping("getCarOwnerUseStation.htm")
    public Result getCarOwnerUseStation(BaseParam baseParam, StationReq req) {
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (passport == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
        if (carowner == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        List<StationResp> resps = stationService.passportUseStations(carowner, req);
        return new Result(Code.SUCCESS, createList(resps));
    }

    /**
     * HYD0302.1运营商/物业充电站列表
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864586
     *
     * @param baseParam
     * @param sort
     * @param keyword
     * @param page
     * @return
     */
    @RequestMapping("getOperatorStations.htm")
    public Result getOperatorStations(BaseParam baseParam, String sort, String city, String keyword, Pagination page) {
        validateParam(baseParam.getPassportId());
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        //权限验证
        if (!PassportTypeEnum.OPERATOR.getValue().equals(passport.getType()) && !PassportTypeEnum.ESTATE.getValue().equals(passport.getType())) {
            return new Result(Code.ERROR, CodeMessage.NO_AUTH);
        }
        List<StationResp> resps = stationService.getOperatorStations(baseParam.getPassportId(), sort, city, keyword, page.page());
        return new PageResult(resps, page.getPaginator());
    }

    /**
     * HYD0302充电站收藏列表[C]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864543
     *
     * @param lngE5
     * @param latE5
     * @param page
     * @return
     */
    @RequestMapping("getMyStations.htm")
    public Result getMyStations(BaseParam baseParam, @RequestParam(defaultValue = "-1") int lngE5, @RequestParam(defaultValue = "-1") int latE5, Pagination page) {
        validateParam(baseParam.getPassportId());
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        List<StationResp> resps = stationService.getMyStations(baseParam.getPassportId(), lngE5, latE5, page.page());
        return new PageResult(resps, page.getPaginator());
    }

    /**
     * HYD0303收藏充电站[C]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864545
     *
     * @param stationId
     * @return
     */
    @RequestMapping("collectStation.htm")
    public Result collectStation(BaseParam baseParam, String stationId) {
        validateParam(baseParam.getPassportId(), stationId);
        return stationService.collectStation(baseParam.getPassportId(), stationId);
    }

    /**
     * HYD0304取消收藏充电站[C]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864547
     *
     * @param baseParam
     * @param stationId
     * @return
     */
    @RequestMapping("cancelCollectStation.htm")
    public Result cancelCollectStation(BaseParam baseParam, String stationId) {
        validateParam(baseParam.getPassportId(), stationId);
        return stationService.cancelCollectStation(baseParam.getPassportId(), stationId);
    }

    /**
     * HYD0305充电站详情[C/O/E]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864549
     *
     * @param baseParam
     * @param stationId
     * @return
     */
    @RequestMapping("getStationDetail.htm")
    public Result getStationDetail(BaseParam baseParam, String stationId) throws ParseException {
        return stationService.getStationDetail(baseParam.getPassportId(), stationId);
    }

    /**
     * HYD0305.1根据qrcode获取充电口
     * http://wiki.jopool.net/pages/viewpage.action?pageId=8487039
     *
     * @param baseParam
     * @param qrCode
     * @return
     */
    @RequestMapping("getPortByQrcode.htm")
    public Result getPortByQrcode(BaseParam baseParam, String qrCode) {
        validateParam(baseParam.getPassportId(), qrCode);
        StationPort stationPort = stationService.getPortByQrcode(qrCode);
        if (null == stationPort) {
            return new Result(Code.ERROR, CodeMessage.STATION_PORT_NOT_EXIST);
        }
        return new Result(Code.SUCCESS, new StationPortResp(stationPort));
    }

    /**
     * HYD0306扫码充电[C]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864570
     *
     * @param baseParam
     * @param portId
     * @param payType
     * @return
     */
    @RequestMapping("startRecharge.htm")
    public Result startRecharge(BaseParam baseParam, String portId, String consumePackageItemId, String payType, String chargeType) {
        validateParam(baseParam.getPassportId(), portId, payType);
        return stationService.startRecharge(baseParam.getPassportId(), portId, consumePackageItemId, payType, chargeType);
    }

    /**
     * HYD0307断开充电[C/O]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864574
     *
     * @param baseParam
     * @param portIds
     * @return
     */
    @RequestMapping("stopRecharge.htm")
    public Result stopRecharge(BaseParam baseParam, String portIds) {
        validateParam(baseParam.getPassportId(), portIds);
        return orderService.stopRecharge(baseParam.getPassportId(), portIds);
    }

    /**
     * HYD0308.1结束订单[O]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864626
     *
     * @param baseParam
     * @param portIds
     * @return
     */
    @RequestMapping("finishOrder.htm")
    public Result finishOrder(BaseParam baseParam, String portIds) {
        validateParam(baseParam.getPassportId(), portIds);
        return orderService.finishOrder(baseParam.getPassportId(), portIds);
    }

    /**
     * HYD0308继续充电
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864572
     *
     * @param baseParam
     * @param orderId
     * @return
     */
    @RequestMapping("continueRecharge.htm")
    public Result continueRecharge(BaseParam baseParam, String orderId) {
        return orderService.continueRecharge(baseParam.getPassportId(), orderId);
    }

    /**
     * HYD0309故障上报
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864584
     *
     * @param baseParam
     * @param req
     * @return
     */
    @RequestMapping("reportFault.htm")
    public Result reportFault(BaseParam baseParam, ReportFaultReq req) {
        StationFault stationFault = req.parseStationFault(baseParam.getPassportId());
        return orderService.reportFault(stationFault);
    }

    /**
     * HYD0310充电站设置[O]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864598
     *
     * @param baseParam
     * @param req
     * @return
     */
    @RequestMapping("modifyStation.htm")
    public Result modifyStation(BaseParam baseParam, ModifyStationReq req) {
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (!PassportTypeEnum.OPERATOR.getValue().equals(passport.getType())) {
            return new Result(Code.ERROR, CodeMessage.NO_AUTH);
        }
        return stationService.modifyStationSetUp(baseParam.getPassportId(), req);
    }

    /**
     * HYD0311充电口设置[O]
     * http://wiki.jopool.net/pages/viewpage.action?pageId=7864600
     *
     * @param baseParam
     * @param req
     * @return
     */
    @RequestMapping("modifyStationPort.htm")
    public Result modifyStationPort(BaseParam baseParam, ModifyStationPortReq req) {
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (!PassportTypeEnum.OPERATOR.getValue().equals(passport.getType())) {
            return new Result(Code.ERROR, CodeMessage.NO_AUTH);
        }
        return stationService.modifyStationPortSetUp(baseParam.getPassportId(), req);
    }


    /**
     * 刷新坐标位置
     *
     * @return
     */
    @RequestMapping("freshLBSRect.htm")
    public String freshLBSRect() {
        List<Station> stations = stationService.getAllStation();
        for (Station station : stations) {
            int lngE5 = station.getLngE5();
            int latE5 = station.getLatE5();
            String key = LBSUtils.getKey(lngE5, latE5);
            redisProvider.hset(Constants.REDIS_KEY_LBS_RECT + key, station.getId(), lngE5 + "," + latE5);
        }
        //
//        redisProvider.hgetAll("key");
//        redisProvider.hdel("key","shopId");
        return "redirect:index.htm";
    }


    /**
     * 电站报警列表
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping("getWarningList.htm")
    public Result getWarningList(BaseParam baseParam, @RequestParam(defaultValue = "0") int type, Pagination page) {
        Passport passport = passportService.getById(baseParam.getPassportId());
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        List<WarningVo> list = stationService.getWarningList(passport,1, type, page.page());
        return new Result(Code.SUCCESS, createList(list));
    }

}
