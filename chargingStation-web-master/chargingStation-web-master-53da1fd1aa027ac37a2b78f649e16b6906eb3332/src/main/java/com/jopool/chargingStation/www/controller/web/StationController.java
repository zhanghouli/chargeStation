package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.utils.LBSUtils;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.enums.StationPortStatusEnum;
import com.jopool.chargingStation.www.enums.StationStatusEnum;
import com.jopool.chargingStation.www.service.*;
import com.jopool.chargingStation.www.vo.*;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.chargingStation.www.vo.common.SearchStationFaultVo;
import com.jopool.chargingStation.www.vo.common.SearchStationVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.redis.RedisProvider;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by synn on 2017/8/29.
 */
@RestController
@RequestMapping("/station")
public class StationController extends BaseController {

    @Resource
    private StationService        stationService;
    @Resource
    private EstateService         estateService;
    @Resource
    private OperatorService       operatorService;
    @Resource
    private RedisProvider         redisProvider;
    @Resource
    private PassportService       passportService;
    @Resource
    private ConsumePackageService consumePackageService;
    @Resource
    private OrderService          orderService;
    @Resource
    private CommonService         commonService;


    /**
     * 电站 列表
     *
     * @param searchStationVo
     * @param page
     * @return
     */
    @RequestMapping("stationList.htm")
    public ModelAndView stationList(SearchStationVo searchStationVo, Pagination page) {
        Passport passport = passportService.getById(getSessionUser().getPassportId());
        //政府人员 看到 省市区电站
        if (PassportTypeEnum.GOVERNMENT.getValue().equals(passport.getType())) {
            setGovernmentCity(getSessionUser().getPassportId(), searchStationVo);
        }
        List<StationVo> stationVoList = stationService.searchStationVo(searchStationVo, page.page());
        //返回一个物业
        Estate estate = estateService.searchEstateById(searchStationVo.getEstateId());
        ModelAndView mv = getPageMv("station/stationList", stationVoList, page);
        return mv.addObject("status", searchStationVo.getStatus())
                .addObject("keyword", searchStationVo.getKeyword())
                .addObject("searchStationVo", searchStationVo)
                .addObject("estate", estate)
                .addObject("searchProvince", searchStationVo.getSearchProvince())
                .addObject("searchCity", searchStationVo.getSearchCity())
                .addObject("searchTown", searchStationVo.getSearchTown())
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId())
                .addObject("passport", passport);
    }

    /**
     * 异常电站
     *
     * @param searchStationVo
     * @param page
     * @return
     */
    @RequestMapping("errorStationList.htm")
    public ModelAndView errorStationList(SearchStationVo searchStationVo, Pagination page) {
        Passport passport = passportService.getById(getSessionUser().getPassportId());
        //政府人员 看到 省市区电站
        if (PassportTypeEnum.GOVERNMENT.getValue().equals(passport.getType())) {
            setGovernmentCity(getSessionUser().getPassportId(), searchStationVo);
        }
        searchStationVo.setStatus(StationStatusEnum.FAULT.getValue());
        List<StationVo> stationVoList = stationService.searchStationVo(searchStationVo, page.page());
        //返回一个物业
        Estate estate = estateService.searchEstateById(searchStationVo.getEstateId());
        ModelAndView mv = getPageMv("station/errorStationList", stationVoList, page);
        return mv.addObject("status", searchStationVo.getStatus())
                .addObject("keyword", searchStationVo.getKeyword())
                .addObject("searchStationVo", searchStationVo)
                .addObject("estate", estate)
                .addObject("searchProvince", searchStationVo.getSearchProvince())
                .addObject("searchCity", searchStationVo.getSearchCity())
                .addObject("searchTown", searchStationVo.getSearchTown())
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId())
                .addObject("passport", passport);
    }

    /**
     * 电站 详情
     *
     * @param stationId
     * @return
     */
    @RequestMapping("stationDetails.htm")
    public ModelAndView stationDetails(String stationId, String timeStartStr, String timeEndStr, SearchStationVo searchStationVo) {
        DateParam dateParam;
        if (StringUtils.isEmpty(timeStartStr) && StringUtils.isEmpty(timeEndStr)) {
            dateParam = getDefaultDateParam();
        } else {
            dateParam = getDateParam(timeStartStr, timeEndStr);
        }
        List<StationRealTimeListen> stationRealTimeListens = stationService.getStationListen(stationId, dateParam);
        Station station = stationService.getById(stationId);
        return new ModelAndView("station/stationDetails")
                .addObject("stationId", stationId)
                .addObject("station", station)
                .addObject("stationRealTimeListens", stationRealTimeListens)
                .addObject("operatorId", searchStationVo.getOperatorId())
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("timeStartStr", DateUtils.date2StringByDay(dateParam.getTimeStart()))
                .addObject("timeEndStr", DateUtils.date2StringByDay(dateParam.getTimeEnd()));
    }

    /**
     * 电站 充电口 列表
     *
     * @return
     */
    @RequestMapping("stationPortList.htm")
    public ModelAndView stationPortList(String stationId, SearchStationVo searchStationVo) {
        List<StationPort> stationPorts = stationService.searchStationList(stationId);
        Station station = stationService.searchByStationId(stationId);
        ModelAndView mv = getSessionUserMV("station/stationPortList");
        return mv.addObject("station", station)
                .addObject("stationPorts", stationPorts)
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId());
    }

    /**
     * 电站 维护日志列表
     *
     * @param stationId
     * @param page
     * @return
     */
    @RequestMapping("stationMaintainLogList.htm")
    public ModelAndView stationMaintainLogList(String stationId, SearchStationVo searchStationVo, Pagination page) {
        List<StationMaintainLog> stationMaintainLogs = stationService.searchStationMaintainLogs(stationId, page.page());
        ModelAndView mv = getPageMv("station/stationMaintainLogList", stationMaintainLogs, page);
        return mv.addObject("stationId", stationId)
                .addObject("searchStationVo", searchStationVo)
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId());
    }

    /**
     * 单个 电站  接口 信息
     *
     * @param stationPortId
     * @param timeStartStr
     * @param timeEndStr
     * @return
     */
    @RequestMapping("stationPort.htm")
    public ModelAndView stationPort(String stationPortId, String timeStartStr, String timeEndStr, SearchStationVo searchStationVo) throws ParseException {
        StationPortInfoVo stationPortInfoVo = stationService.getStationPortInfo(stationPortId);
        ModelAndView mv = getSessionUserMV("station/stationPort");
        return mv.addObject("stationPortId", stationPortId)
                .addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr)
                .addObject("stationPortInfoVo", stationPortInfoVo)
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId())
                .addObject("stationId", searchStationVo.getStationId());
    }


    /**
     * 电站 单个 接口 充值 记录
     *
     * @param stationPortId
     * @return
     */
    @RequestMapping("stationOrder.htm")
    public ModelAndView stationOrder(String stationPortId, SearchBaseVo searchBaseVo, String timeStartStr, String timeEndStr, Pagination page, SearchStationVo searchStationVo) throws ParseException {
        DateParam dateParam = new DateParam(timeStartStr, timeEndStr);
        List<StationPortOrderVo> list = orderService.listStationPortIdAndOrder(stationPortId, dateParam, searchBaseVo, page);
        ModelAndView mv = getPageMv("station/stationOrder", list, page);
        return mv.addObject("stationPortId", stationPortId)
                .addObject("keyword", searchBaseVo.getKeyword())
                .addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr)
                .addObject("estateId", searchStationVo.getEstateId())
                .addObject("operatorId", searchStationVo.getOperatorId())
                .addObject("stationId", searchStationVo.getStationId());
    }


    /**
     * 电站 故障 列表
     *
     * @return
     */
    @RequestMapping("stationFaultList.htm")
    public ModelAndView stationFaultList(SearchStationFaultVo searchStationFaultVo, Pagination page) {
        List<StationFaultVo> stationFaultVos = stationService.searchStationFaultVo(searchStationFaultVo, page.page());
        ModelAndView mv = getPageMv("station/stationFaultList", stationFaultVos, page);
        return mv.addObject("isViewed", searchStationFaultVo.getIsViewed())
                .addObject("keyword", searchStationFaultVo.getKeyword());
    }


    /**
     * 故障 电站 详情
     *
     * @param stationFaultId
     * @return
     */
    @RequestMapping("stationFaultInfo.htm")
    public ModelAndView stationFaultInfo(String stationFaultId) {
        StationFault stationFault = stationService.getByStationFaultId(stationFaultId);
        List<String> picList = new ArrayList<String>();
        if (!StringUtils.isEmpty(stationFault.getPics())) {
            String[] pics = stationFault.getPics().split(",");
            for (String pic : Arrays.asList(pics)) {
                picList.add(pic);
            }
        }
        ModelAndView mv = getSessionUserMV("station/stationFaultInfo");
        return mv.addObject("stationFault", stationFault)
                .addObject("pics", picList);
    }

    /**
     * 电站 添加 or 修改
     *
     * @param station
     * @return
     */
    @RequestMapping("doAddOrModifyStation.htm")
    public Result doAddOrModifyStation(Station station, String batchNumber) {
        String[] areas = station.getArea().split(",", -1);
        OpenArea openArea = null;
        //城市开通 验证
        if (areas.length > 0) {
            CommonArea commonArea = commonService.getByCommonAreaCode(areas[1]);
            openArea = commonService.getAreaId(commonArea.getId());
        }
        if (openArea == null) {
            return new Result(Code.ERROR, "此城市暂未开通，请先进行城市开通");
        }
        if (!StringUtils.isEmpty(station.getId())) {
            Station odlStation = stationService.searchByStationId(station.getId());
            if (odlStation == null) {
                return new Result(Code.ERROR, CodeMessage.ESTATE_NOT_EXIST);
            }
            odlStation.setNumber(station.getNumber());
            odlStation.setName(station.getName());
            odlStation.setArea(station.getArea());
            odlStation.setAreaDes(station.getAreaDes());
            Station stationNumber = stationService.getByNumber(station.getNumber());
            //充电站编码不重复
            if (stationNumber != null && !station.getNumber().equals(stationNumber.getNumber())) {
                return new Result(Code.ERROR, CodeMessage.STATION_NUMBER_EXIST);
            }
            if (station.getLngE5() > 0 && station.getLatE5() > 0) {
                stationService.addToSolr(station);
            }
            //
            odlStation.setLatE5(station.getLatE5() != 0 ? station.getLatE5() : odlStation.getLatE5());
            odlStation.setLngE5(station.getLngE5() != 0 ? station.getLngE5() : odlStation.getLngE5());
            odlStation.setAddress(station.getAddress());
            odlStation.setPortCount(station.getPortCount());
            odlStation.setElectricBill(station.getElectricBill());
            odlStation.setConsumePackageId(station.getConsumePackageId());
            odlStation.setOperatorSharingRatio(station.getOperatorSharingRatio());
            odlStation.setEstateSharingRatio(station.getEstateSharingRatio());
            odlStation.setModifier(getSessionUser().getPassportId());
            odlStation.setModifyTime(new Date());
            stationService.modifyStation(odlStation);
        } else {
            Estate estate = passportService.getByEstateId(station.getEstateId());
            if (estate == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //充电站编码不重复
            Station stationNumber = stationService.getByNumber(station.getNumber());
            if (stationNumber != null) {
                return new Result(Code.ERROR, CodeMessage.STATION_NUMBER_EXIST);
            }
            //isVariable 是否 批量追加的
            addStation(station, estate, true, batchNumber);
            if (!StringUtils.isEmpty(batchNumber)) {
                batchAddStation(station, estate, batchNumber);
            }


        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 电站 充电口 添加 修改
     *
     * @param stationPort
     * @return
     */
    @RequestMapping("addStationPort.htm")
    public Result addStationPort(StationPort stationPort) {
        if (!StringUtils.isEmpty(stationPort.getId())) {
            StationPort stationPortOld = stationService.getPortById(stationPort.getId());
            if (stationPortOld == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //充电口 二维码 判重
            StationPort stationPortQrCode = stationService.getPortByQrcode(stationPort.getQrCode());
            if (stationPortQrCode != null && !stationPort.getQrCode().equals(stationPortQrCode.getQrCode())) {
                return new Result(Code.ERROR, CodeMessage.STATIOPORT_QRCODE_EXIST);
            }
            stationPortOld.setNumber(stationPort.getNumber());
            stationPortOld.setQrCode(stationPort.getQrCode());
            stationPortOld.setMaxPower(stationPort.getMaxPower());
            stationPortOld.setMinPower(stationPort.getMinPower());
            stationPortOld.setTrickleTime(stationPort.getTrickleTime());
            stationPortOld.setIsAutoStop(stationPort.getIsAutoStop());
            stationPortOld.setIsLargePower(stationPort.getIsLargePower());
            stationPortOld.setModifyTime(new Date());
            stationService.modifyStationPort(stationPortOld);

        } else {

            Station station = stationService.searchByStationId(stationPort.getStationId());
            int portCounts = stationService.searchStationId(station.getId());
            if (station.getPortCount().compareTo(portCounts) <= 0) {
                return new Result(Code.ERROR, "充电口已经满了");
            }
            //充电口 二维码 判重
            StationPort stationPortQrCode = stationService.getPortByQrcode(stationPort.getQrCode());
            if (stationPortQrCode != null) {
                return new Result(Code.ERROR, CodeMessage.STATIOPORT_QRCODE_EXIST);
            }
            StationPort newStationPort = new StationPort();
            newStationPort.setId(UUIDUtils.createId());
            newStationPort.setSeq(portCounts);
            newStationPort.setNumber(stationPort.getNumber());
            newStationPort.setStatus(StationPortStatusEnum.FAULT.getValue());
            newStationPort.setStationId(stationPort.getStationId());
            newStationPort.setQrCode(stationPort.getQrCode());
            newStationPort.setMaxPower(stationPort.getMaxPower());
            newStationPort.setMinPower(stationPort.getMinPower());
            newStationPort.setTrickleTime(stationPort.getTrickleTime());
            newStationPort.setIsAutoStop(stationPort.getIsAutoStop());
            newStationPort.setIsLargePower(stationPort.getIsLargePower());
            newStationPort.setIsEnabled(true);
            newStationPort.setIsDeleted(false);
            newStationPort.setCreationTime(new Date());
            newStationPort.setCreator(getSessionUser().getPassportId());
            stationService.addStationPort(newStationPort);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 故障 电站 提交 处理 结果
     *
     * @param stationFault
     * @return
     */
    @RequestMapping("modifyStationFault.htm")
    public Result modifyStationFault(StationFault stationFault) {
        return stationService.modifyStationFault(stationFault);
    }

    /**
     * 电站 详情
     *
     * @param stationId
     * @return
     */
    @RequestMapping("toggleStationId.htm")
    public Result toggleStationId(String stationId) {
        validateParam(stationId);
        Station station = stationService.searchByStationId(stationId);
        List<ConsumePackage> consumePackages = consumePackageService.searchOperatorId(station.getOperatorId());
        StationPackageVo stationPackageVo = new StationPackageVo(station);
        Map<String, String> map = new HashMap<String, String>();
        for (ConsumePackage consumePackage : consumePackages) {
            map.put(consumePackage.getId(), consumePackage.getName());
        }
        stationPackageVo.setMaps(map);
        return new Result(Code.SUCCESS, stationPackageVo);
    }

    /**
     * 电站 充电口 详情
     *
     * @param stationPortId
     * @return
     */
    @RequestMapping("getStationPort.htm")
    public Result getStationPort(String stationPortId) {
        validateParam(stationPortId);
        StationPort stationPort = stationService.getPortById(stationPortId);
        return new Result(Code.SUCCESS, stationPort);
    }

    /**
     * 电站 删除
     *
     * @param stationId
     * @return
     */
    @RequestMapping("removeStationId.htm")
    public Result removeStationId(String stationId) {
        validateParam(stationId);
        return stationService.removeStationById(stationId);
    }

    /**
     * 删除 电站 故障 信息
     *
     * @param stationFaultId
     * @return
     */
    @RequestMapping("removeStationFault.htm")
    public Result removeStationFault(String stationFaultId) {
        validateParam(stationFaultId);
        return stationService.removeStationFault(stationFaultId);
    }

    /**
     * 电口 删除
     *
     * @param stationPortId
     * @return
     */
    @RequestMapping("doRemoveStationPortId.htm")
    public Result doRemoveStationPort(String stationPortId) {
        validateParam(stationPortId);
        stationService.removeStationPortId(stationPortId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 删除 订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("doRemoveOrder.htm")
    public Result doRemoveOrder(String orderId) {
        validateParam(orderId);
        return orderService.removeOrderId(orderId);
    }


    /**
     * 订单查看电流
     *
     * @param portId
     * @return
     */
    @RequestMapping("orderStationPortDetails.htm")
    public Result orderStationPortDetails(String portId) {
        validateParam(portId);
        List<StationPortRealTimeListen> stationPortRealTimeListenList = stationService.getOrderStationPortDetails(portId);
        return new Result(Code.SUCCESS, createList(stationPortRealTimeListenList));
    }

    /**
     * 充电站电口历史数据
     *
     * @param portId
     * @return
     */
    @RequestMapping("getStationPortListenByPortId.htm")
    public Result getStationPortListenByPortId(String portId, String timeStartStr, String timeEndStr) {
        validateParam(portId);
        DateParam dateParam;
        if (StringUtils.isEmpty(timeStartStr) && StringUtils.isEmpty(timeEndStr)) {
            dateParam = getDefaultDateParam();
        } else {
            dateParam = getDateParam(timeStartStr, timeEndStr);
        }
        List<StationPortRealTimeListen> portListens = stationService.getStationPortListen(portId, dateParam);
        return new Result(Code.SUCCESS, createList(portListens));
    }

    /**
     * 每日 电流  总和  数据图
     *
     * @param portId
     * @param timeStartStr
     * @param timeEndStr
     * @return
     * @throws ParseException
     */
    @RequestMapping("getStationPortDateSum.htm")
    public Result getStationPortDateSum(String portId, String timeStartStr, String timeEndStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //日期 对应功率
        List<StationPortPowerVo> stationPortPowerVo = orderService.listOrderByPortIdAndDate(portId, timeStartStr == null || timeStartStr == "" ? null : simpleDateFormat.parse(timeStartStr), timeEndStr == null || timeEndStr == "" ? null : simpleDateFormat.parse(timeEndStr));
        return new Result(Code.SUCCESS, createList(stationPortPowerVo));
    }

    /**
     * 电站报警列表
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping("warningList.htm")
    public ModelAndView warningList(@RequestParam(defaultValue = "0") int type, SearchStationVo searchStationVo, String timeStartStr, String timeEndStr, Pagination page) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateParam dateParam = new DateParam(timeStartStr, timeEndStr);
        //政府人员 看到 省市区电站
        Passport passport = passportService.getById(getSessionUser().getPassportId());
        if (PassportTypeEnum.GOVERNMENT.getValue().equals(passport.getType())) {
            Government government = passportService.getGovernmentByPassportId(getSessionUser().getPassportId());
            if (null != government) {
                String[] area = government.getArea().split(",", -1);
                if (!StringUtils.isEmpty(area[0])) {
                    searchStationVo.setSearchProvince(area[0]);
                }
                if (!StringUtils.isEmpty(area[1])) {
                    searchStationVo.setSearchCity(area[1]);
                }
                if (!StringUtils.isEmpty(area[2])) {
                    searchStationVo.setSearchTown(area[2]);
                }
            }
        }
        List<WarningVo> warningVoList = stationService.searchWarningList(0, type, searchStationVo, dateParam, page.page());
        ModelAndView mv = getPageMv("station/warningList", warningVoList, page);
        return mv.addObject("type", type)
                .addObject("timeStartStr", timeStartStr)
                .addObject("timeEndStr", timeEndStr)
                .addObject("searchProvince", searchStationVo.getSearchProvince())
                .addObject("searchCity", searchStationVo.getSearchCity())
                .addObject("searchTown", searchStationVo.getSearchTown())
                .addObject("keyword", searchStationVo.getKeyword())
                .addObject("passport", passport);
    }

    /**
     * @param station
     * @param estate
     * @param batchNumber
     */
    public void batchAddStation(Station station, Estate estate, String batchNumber) {
        String[] sixTeen = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String firstNumber = station.getNumber().substring(station.getNumber().length() - 1, station.getNumber().length());
        String lastNumber = batchNumber.substring(batchNumber.length() - 1, batchNumber.length());
        //不完整 编号
        String incompletenessNumber = batchNumber.substring(0, batchNumber.length() - 1);
        boolean isVariable = false;
        for (int i = 0; i < sixTeen.length; i++) {
            Station stationNumber = stationService.getByNumber(incompletenessNumber + sixTeen[i]);
            if (stationNumber != null) {
                if (sixTeen[i].equals(firstNumber)) {
                    isVariable = true;
                }
                // 匹配当前是否为末编号
                if (sixTeen[i].equals(lastNumber)) {
                    isVariable = false;
                }
                continue;
            }
            if (isVariable) {
                addStation(station, estate, false, incompletenessNumber + sixTeen[i]);
            }
            //匹配 首编号
            if (sixTeen[i].equals(firstNumber)) {
                isVariable = true;
            }
            // 匹配当前是否为末编号
            if (sixTeen[i].equals(lastNumber)) {
                isVariable = false;
            }
        }
    }

    /**
     * 电站 添加
     *
     * @param station
     * @param estate
     */
    public void addStation(Station station, Estate estate, boolean isVariable, String stationNumber) {
        Station newStation = new Station();
        newStation.setId(UUIDUtils.createId());
        newStation.setOperatorId(station.getOperatorId());
        newStation.setEstateId(station.getEstateId());
        newStation.setNumber(isVariable ? station.getNumber() : stationNumber);
        newStation.setName(isVariable ? station.getName() : stationNumber);
        newStation.setArea(station.getArea());
        newStation.setAreaDes(station.getAreaDes());
        String key = LBSUtils.getKey(station.getLngE5(), station.getLatE5());
        redisProvider.hdel(key, station.getId());
        redisProvider.hset(Constants.REDIS_KEY_LBS_RECT + key, station.getId(), station.getLngE5() + "," + station.getLatE5());
        newStation.setLatE5(station.getLatE5());
        newStation.setLngE5(station.getLngE5());
        newStation.setAddress(station.getAddress());
        newStation.setPortCount(station.getPortCount());
        newStation.setConsumePackageId(station.getConsumePackageId());
        newStation.setOperatorSharingRatio(station.getOperatorSharingRatio());
        newStation.setEstateSharingRatio(station.getEstateSharingRatio());
        newStation.setStatus(StationStatusEnum.FAULT.getValue());
        newStation.setElectricBill(estate.getElectricBill());
        newStation.setCreationTime(new Date());
        newStation.setIsDeleted(false);
        newStation.setIsEnabled(true);
        newStation.setCreator(getSessionUser().getPassportId());
        stationService.addStation(newStation);
        OpetatorStationKey opetatorStationKey = new OpetatorStationKey();
        opetatorStationKey.setOperatorId(station.getOperatorId());
        opetatorStationKey.setStationId(newStation.getId());
        operatorService.addOperatorStation(opetatorStationKey);
        //新增充电站时  添加充电口
        stationService.addStationPortAccordingToPort(newStation);
    }

    /**
     * 更新电站solr.
     *
     * @return
     */
    @RequestMapping("updateStationSolr.htm")
    public Result updateStationSolr() {
        stationService.updateStationSolr();
        return new Result(Code.SUCCESS);
    }

    /**
     * 维护内容 新增或修改
     *
     * @param stationMaintainLog
     * @return
     */
    @RequestMapping("addOrModifyStationMaintainLog.htm")
    public Result addOrModifyStationMaintainLog(StationMaintainLog stationMaintainLog) {
        if (!StringUtils.isEmpty(stationMaintainLog.getId())) {
            StationMaintainLog stationMaintainLogOld = stationService.getStationMaintainLogId(stationMaintainLog.getId());
            if (stationMaintainLogOld == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            stationMaintainLogOld.setContent(stationMaintainLog.getContent());
            stationMaintainLogOld.setModifyTime(new Date());
            stationMaintainLogOld.setModifier(getSessionUser().getPassportId());
            stationService.modifyStationMaintainLog(stationMaintainLogOld);
        } else {
            stationMaintainLog.setId(UUIDUtils.createId());
            stationMaintainLog.setPassportId(getSessionUser().getPassportId());
            stationMaintainLog.setDeleted(false);
            stationMaintainLog.setCreationTime(new Date());
            stationMaintainLog.setCreator(getSessionUser().getPassportId());
            stationService.addStationMaintainLog(stationMaintainLog);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 查看修改
     *
     * @param stationMaintainLogId
     * @return
     */
    @RequestMapping("changeStationMaintainLog.htm")
    public Result changeStationMaintainLog(String stationMaintainLogId) {
        validateParam(stationMaintainLogId);
        StationMaintainLog stationMaintainLog = stationService.getStationMaintainLogId(stationMaintainLogId);
        return new Result(Code.SUCCESS, stationMaintainLog);
    }

    /**
     * 获取所有 物业
     *
     * @return
     */
    @RequestMapping("getAllEstate.htm")
    public Result getAllEstate() {
        return new Result(Code.SUCCESS, passportService.getEstateAll());
    }

    /**
     * 替换电站物业
     *
     * @param stationId        电站ID
     * @param estateId         物业id
     * @param consumePackageId 套装ID
     * @return
     */
    @RequestMapping("modifyStationReplaceEstate.htm")
    public Result modifyStationReplaceEstate(String stationId, String estateId, String consumePackageId) {
        validateOrParam(stationId, estateId, consumePackageId);
        Station station = stationService.getById(stationId);
        if (station == null) {
            return new Result(Code.ERROR, CodeMessage.STATION_NOT_EXIST);
        }
        Estate estate = estateService.getById(estateId);
        if (estate == null) {
            return new Result(Code.ERROR, "物业不存在");
        }
        ConsumePackage consumePackage = consumePackageService.searchConsumePackageById(consumePackageId);
        if (consumePackage == null) {
            return new Result(Code.ERROR, "套餐不存在");
        }
        Operator operator = operatorService.getById(estate.getOperatorId());
        if (operator == null) {
            return new Result(Code.ERROR, "运营商不存在");
        }
        station.setOperatorId(operator.getId());
        station.setEstateId(estateId);
        station.setConsumePackageId(consumePackageId);
        stationService.modifyStation(station);
        return new Result(Code.SUCCESS);
    }

    /**
     * 日志删除
     *
     * @param stationMaintainLogId
     * @return
     */
    @RequestMapping("removeStationMaintainLog.htm")
    public Result removeStationMaintainLog(String stationMaintainLogId) {
        validateParam(stationMaintainLogId);
        stationService.removeStationMaintainLogId(stationMaintainLogId);
        return new Result(Code.SUCCESS);
    }


    /**
     * 政府 查看 区域 电站
     *
     * @param passportId
     * @param searchStationVo
     */
    private void setGovernmentCity(String passportId, SearchStationVo searchStationVo) {
        Government government = passportService.getGovernmentByPassportId(passportId);
        if (null != government) {
            String[] area = government.getArea().split(",", -1);
            if (!StringUtils.isEmpty(area[0])) {
                searchStationVo.setSearchProvince(area[0]);
            }
            if (!StringUtils.isEmpty(area[1])) {
                searchStationVo.setSearchCity(area[1]);
            }
        }
    }


}
