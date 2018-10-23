package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.apppush.AppPushService;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.base.utils.CharUtil;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.constants.SystemParamKeys;
import com.jopool.chargingStation.www.dao.*;
import com.jopool.chargingStation.www.enums.*;
import com.jopool.chargingStation.www.request.ModifyStationPortReq;
import com.jopool.chargingStation.www.request.ModifyStationReq;
import com.jopool.chargingStation.www.request.StationReq;
import com.jopool.chargingStation.www.response.*;
import com.jopool.chargingStation.www.service.*;
import com.jopool.chargingStation.www.vo.*;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchStationFaultVo;
import com.jopool.chargingStation.www.vo.common.SearchStationVo;
import com.jopool.chargingStation.www.vo.solr.StationBean;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.redis.RedisProvider;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.MathUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by synn on 2017/8/29.
 */
@Service
public class StationServiceImpl extends BaseServiceImpl implements StationService, SelfBeanAware<StationService> {

    private StationService                  selfService;
    @Resource
    private OperatorService                 operatorService;
    @Resource
    private ConsumePackageService           consumePackageService;
    @Resource
    private EstateService                   estateService;
    @Resource
    private PassportService                 passportService;
    @Resource
    private OrderService                    orderService;
    @Resource
    private StationMapper                   stationMapper;
    @Resource
    private StationPortMapper               stationPortMapper;
    @Resource
    private CarownerStationMapper           carownerStationMapper;
    @Resource
    private StationSnapshotMapper           stationSnapshotMapper;
    @Resource
    private StationPortSnapshotMapper       stationPortSnapshotMapper;
    @Resource
    private StationFaultMapper              stationFaultMapper;
    @Resource
    private WarningMapper                   warningMapper;
    @Resource
    private StationRealTimeListenMapper     stationRealTimeListenMapper;
    @Resource
    private StationPortRealTimeListenMapper stationPortRealTimeListenMapper;
    @Resource
    private RedisProvider                   redisProvider;
    @Resource
    private HttpSolrClient                  httpSolrClient;
    @Resource
    private CommonService                   commonService;
    @Resource
    private SystemConfigService             systemConfigService;
    @Resource
    private WxPushService                   wxPushService;
    @Resource
    private StationMaintainLogMapper        stationMaintainLogMapper;
    @Resource
    private GovernmentService               governmentService;
    @Resource
    private AppPushService                  appPushService;

    @Override
    public void setSelfBean(StationService object) {
        this.selfService = object;
    }

    @Override
    public Station getById(String stationId) {
        return stationMapper.selectByPrimaryKey(stationId);
    }

    @Override
    public Station getByNumber(String number) {
        return stationMapper.selectByNumber(number);
    }

    @Override
    public void addStation(Station station) {
        stationMapper.insert(station);
        //
//        String key = LBSUtils.getKey(station.getLngE5(), station.getLatE5());
//        redisProvider.hset(Constants.REDIS_KEY_LBS_RECT + key, station.getId(), station.getLngE5() + "," + station.getLatE5());
        //
        if (station.getLngE5() > 0 && station.getLatE5() > 0) {
            selfService.addToSolr(station);
        }
    }

    @Override
    public Result modifyStation(Station station) {
        stationMapper.updateByPrimaryKeySelective(station);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeStationById(String stationId) {
        stationMapper.deleteByPrimaryKey(stationId);
        List<StationPort> stationPorts = stationPortMapper.selectStationPort(stationId);
        for (StationPort stationPort : stationPorts) {
            stationPortMapper.deleteByPrimaryKey(stationPort.getId());
        }
        try {
            httpSolrClient.deleteById(stationId);
            httpSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public Station searchByStationId(String stationId) {
        return stationMapper.selectByPrimaryKey(stationId);
    }

    @Override
    public List<StationVo> searchStationVo(SearchStationVo stationVo, RowBounds page) {
        stationVo.setAreaID(areaId(stationVo));
        List<StationVo> stations = stationMapper.selectSearchStationVo(stationVo, page);
        List<StationVo> stationVoList = new ArrayList<>();
        if (stations.size() > 0) {
            for (StationVo sv : stations) {
                StationVo stationVoNew = new StationVo(sv);
                if (!StringUtils.isEmpty(stationVoNew.getConsumePackageId())) {
                    ConsumePackage consumePackage = consumePackageService.searchConsumePackageById(stationVoNew.getConsumePackageId());
                    if (consumePackage != null) {
                        stationVoNew.setConsumePackageName(consumePackage.getName());
                    }
                }
                stationVoList.add(stationVoNew);
            }
        }
        return stationVoList;
    }

    @Override
    public void addStationPortAccordingToPort(Station station) {
        for (int i = 0; i < station.getPortCount(); i++) {
            addStationPort(i, station);
        }
    }

    @Override
    public void modifyStationPortAccordingToPort(Station station) {
        //获取此电站充电口 数量
        int portCounts = stationPortMapper.selectStationId(station.getId());
        //获取充电口
        List<StationPort> stationPorts = stationPortMapper.selectStationPort(station.getId());
        if (station.getPortCount().compareTo(portCounts) == -1) {
            //小于以有电站的数量
            for (int i = stationPorts.size() - 1; i >= stationPorts.size() - (portCounts - station.getPortCount()); i--) {
                StationPort stationPort = stationPorts.get(i);
                stationPort.setIsDeleted(true);
                stationPortMapper.updateByPrimaryKeySelective(stationPort);
            }
        } else if (station.getPortCount().compareTo(portCounts) == 1) {
            //大于已有电站
            for (int i = stationPorts.size() - 1; i < station.getPortCount(); i++) {
                addStationPort(i, station);
            }
        }
    }

    @Override
    public Result collectStation(String passportId, String stationId) {
        CarownerStationKey carownerStation = new CarownerStationKey();
        //验证
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (null == carowner) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Station station = stationMapper.selectByPrimaryKey(stationId);
        if (null == station) {
            return new Result(Code.ERROR, CodeMessage.STATION_NOT_EXIST);
        }
        CarownerStationKey carownerStationKey = carownerStationMapper.selectByCarOnerIdAndStationdId(carowner.getId(), stationId);
        if (carownerStationKey != null) {
            return new Result(Code.ERROR, CodeMessage.STATION_COLLECTED);
        }
        //收藏
        carownerStation.setCarownerId(carowner.getId());
        carownerStation.setStationId(stationId);
        carownerStationMapper.insertSelective(carownerStation);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result cancelCollectStation(String passportId, String stationId) {
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (null == carowner) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        //删除数据
        carownerStationMapper.deleteByCarownerAndStationId(carowner.getId(), stationId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result startRecharge(String passportId, String portId, String consumePackageItemId, String payType, String chargeType) {
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        //校验
        StationPort stationPort = stationPortMapper.selectByPrimaryKey(portId);
        if (null == stationPort || stationPort.getIsDeleted() || StationPortStatusEnum.FAULT.getValue().equals(stationPort.getStatus())) {
            return new Result(Code.ERROR, CodeMessage.STATION_PORT_FAULT);
        }
        if (StationPortStatusEnum.WORKING.getValue().equals(stationPort.getStatus()) || StationPortStatusEnum.SUSPEND.getValue().equals(stationPort.getStatus())) {
            return new Result(Code.ERROR, CodeMessage.STATION_PORT_USING);
        }
        Station station = stationMapper.selectByPrimaryKey(stationPort.getStationId());
        if (null == station || station.getIsDeleted() || station.getStatus() == StationStatusEnum.FAULT.getValue()) {
            return new Result(Code.ERROR, CodeMessage.STATION_FAULT);
        }
        Passport passport = passportService.getById(passportId);
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (null == carowner) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }

        if (ChargeTypeEnum.ELECTRICITY.getValue().equals(chargeType)) {
            PassportAccount passportAccount = passportService.getPassportAmountByPassportId(passportId);
            CommonSystemConfig minAccount = systemConfigService.getConfigByName(Constants.MIN_AMOUNT_ELECTRICITY_CHARGE);
            if (minAccount != null && passportAccount.getAmount() < Integer.valueOf(minAccount.getVal())) {
                //余额不足
                return new Result(Code.ERROR, CodeMessage.AMOUNT_NOT_ENOUGH);
            }
        }
        ConsumePackageItem consumePackageItem = new ConsumePackageItem();
        if (!ChargeTypeEnum.ELECTRICITY.getValue().equals(chargeType) && !StringUtils.isEmpty(consumePackageItemId)) {
            consumePackageItem = consumePackageService.getById(consumePackageItemId);
            if (null == consumePackageItem) {
                return new Result(Code.ERROR, CodeMessage.PACKAGE_NOT_EXIST);
            }
        }
        //add order
        OrderIdResp order = orderService.addOrder(passport, station, stationPort, consumePackageItem, payType, chargeType);
        //个推 内容
        AppPushMessage appPushMessage = new AppPushMessage("充电开始通知", "充电详情", "http://h5.h1d.com.cn/station/index.html#/member/chaRecDetail?orderId=" + order.getOrderId());
        //账户金额支付充电
        PassportAccount passportAccount = passportService.getPassportAmountByPassportId(passportId);
        if (payType.equals(PayTypeEnum.BALANCE.getValue())) {
            MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.START_CHARGE.getValue());
            if (messageTemplate != null && tokenResp != null) {
                WxPushVo wxPushVo = new WxPushVo(tokenResp, messageTemplate, carowner, passportAccount);
                wxPushService.pushStartCharge(wxPushVo, station, stationPort, orderService.getByOrderId(order.getOrderId()));
            }
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
            }
        }
        //时间余量充电
        if (payType.equals(PayTypeEnum.TIMEPAY.getValue())) {
            MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.TIME_CHARGE.getValue());
            if (messageTemplate != null && tokenResp != null) {
                WxPushVo wxPushVo = new WxPushVo(tokenResp, messageTemplate, carowner, passportAccount);
                wxPushService.pushChargeTime(wxPushVo, station, stationPort, orderService.getByOrderId(order.getOrderId()));
            }
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
            }
        }

        return new Result(Code.SUCCESS, order);
    }

    @Override
    public String addOrderStationSnapshot(Order order, Station station) {
        StationSnapshot stationSnapshot = new StationSnapshot(order.getCreator(), station);
        stationSnapshotMapper.insertSelective(stationSnapshot);
        return stationSnapshot.getId();
    }

    @Override
    public StationPortSnapshot addOrderStationPortSnapshot(Order order, StationPort stationPort) {
        StationPortSnapshot stationPortSnapshot = new StationPortSnapshot(order.getCreator(), stationPort);
        stationPortSnapshotMapper.insertSelective(stationPortSnapshot);
        return stationPortSnapshot;
    }

    @Override
    public Result modifyStationSetUp(String passportId, ModifyStationReq req) {
        String[] stationIds = req.getStationId().split(",", -1);
        for (int i = 0; i < stationIds.length; i++) {
            Station station = stationMapper.selectByPrimaryKey(stationIds[i]);
            if (req.getConsumePackageId() != null && req.getConsumePackageId().length() > 0) {
                station.setConsumePackageId(req.getConsumePackageId());
            }
            station.setEstateSharingRatio(req.getEstateSharingRatio() == 0 ? station.getEstateSharingRatio() : req.getEstateSharingRatio());
            station.setOperatorSharingRatio(req.getOperatorSharingRatio() == 0 ? station.getOperatorSharingRatio() : req.getOperatorSharingRatio());
            station.setAddress(!StringUtils.isEmpty(req.getAddress()) ? req.getAddress() : station.getAddress());
            if (req.getLatE5() != null) {
                station.setLatE5(req.getLatE5() > 0 ? req.getLatE5() : station.getLatE5());
            }
            if (req.getLngE5() != null) {
                station.setLngE5(req.getLngE5() > 0 ? req.getLngE5() : station.getLngE5());
            }
            station.setModifier(passportId);
            station.setElectricBill(req.getElectricBill() == 0 ? station.getElectricBill() : req.getElectricBill());
            stationMapper.updateByPrimaryKeySelective(station);
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result modifyStationPortSetUp(String passportId, ModifyStationPortReq req) {
        String[] ids = req.getStationPortIds().split(",", -1);
        for (int i = 0; i < ids.length; i++) {
            //多个充电口
            StationPort stationPort = stationPortMapper.selectByPrimaryKey(ids[i]);
            if (null != stationPort) {
                stationPort.setIsAutoStop(req.getAutoStop());
                stationPort.setIsLargePower(req.getLargePower());
                stationPort.setMaxPower(req.getMaxPower());
                stationPort.setMinPower(req.getMinPower());
                stationPort.setTrickleTime(req.getTrickleTime());
                if (!StringUtils.isEmpty(req.getQrCode())) {
                    stationPort.setQrCode(req.getQrCode());
                }
                stationPort.setModifier(passportId);
                stationPortMapper.updateByPrimaryKeySelective(stationPort);
            }
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<StationResp> getMyStations(String passportId, int lngE5, int latE5, RowBounds page) {
        List<StationResp> resps = new ArrayList<StationResp>();
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (null != carowner) {
            List<CarownerStationKey> carownerStations = carownerStationMapper.selectByCarownerId(carowner.getId(), page);
            for (CarownerStationKey carownerStation : carownerStations) {
                Station station = stationMapper.selectByPrimaryKey(carownerStation.getStationId());
                if (station == null) {
                    continue;
                }
                StationResp resp = new StationResp(station);
                StationPortCount portCount = selfService.getPortCount(station.getId());
                if (portCount != null) {
                    resp.setPortCount(portCount.getAllCount());
                    resp.setWorkCount(portCount.getWorkingCount());
                    resp.setFreeCount(portCount.getFreeCount());
                }
                resp.setDistance(getDistanceByLngLat(lngE5, latE5, station.getLngE5(), station.getLatE5()));
                resps.add(resp);
            }
        }
        return resps;
    }

    @Override
    public List<StationResp> getNearByStations(StationReq req, int page) {
        List<StationResp> resps = new ArrayList<StationResp>();
        SolrQuery params = new SolrQuery();

        String q_params = "";
        if (!StringUtils.isEmpty(req.getKeyword()) && !StringUtils.isEmpty(req.getCity())) {
            if (CharUtil.isChinese(req.getKeyword())) {
                q_params = "station_name:" + req.getKeyword() + " AND station_address:\"" + req.getCity() + "\"";
            } else {
                q_params = "station_name:*" + req.getKeyword() + "* AND station_address:\"" + req.getCity() + "\"";
            }
        } else if (!StringUtils.isEmpty(req.getKeyword()) && StringUtils.isEmpty(req.getKeyword())) {
            if (CharUtil.isChinese(req.getKeyword())) {
                q_params = "station_name:" + req.getKeyword();
            } else {
                q_params = "station_name:*" + req.getKeyword() + "*";
            }
        } else if (!StringUtils.isEmpty(req.getCity()) && StringUtils.isEmpty(req.getKeyword())) {
            q_params = "station_address:\"" + req.getCity() + "\"";
        }
        if (StringUtils.isEmpty(req.getKeyword()) && StringUtils.isEmpty(req.getCity())) {
            params.set("q", "*:*");
//            params.set("d", 10); //就近 d km的所有数据
//            params.set("fq", "{!geofilt}");           //距离过滤函数
        } else {
            params.set("q", q_params);
        }
        params.set("pt", req.getLngE5() / 1E5 + " " + req.getLatE5() / 1E5); //当前经纬度
        params.set("fl", "*,dist:geodist(),score");//查询的结果中添加距离和score
        params.set("sort", "geodist() asc");  //根据距离排序：由近到远
        params.set("sfield", "station_position"); //经纬度的字段
        params.set("start", (page <= 1 ? 0 : (page - 1) * 10));  //记录开始位置
        params.set("rows", page < 0 ? Integer.MAX_VALUE : 10);  //查询的行数
        //
        List<StationBean> stationBeans = new ArrayList<StationBean>();
        try {
            QueryResponse queryResponse = httpSolrClient.query(params);
            stationBeans = queryResponse.getBeans(StationBean.class);
        } catch (Exception e) {
            log.error("获取附近电站失败:{}", e.getMessage());
        }
        Iterator<StationBean> iterator = stationBeans.iterator();
        resps = getStationListResp(iterator);
        return resps;
    }

    /**
     * 添加到solr
     *
     * @param station
     */
    @Override
    public void addToSolr(Station station) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("station_id", station.getId());
        if (!StringUtils.isEmpty(station.getAreaDes()) && !StringUtils.isEmpty(station.getAddress())) {
            doc.addField("station_address", station.getAreaDes() + "," + station.getAddress());
        }
        if (!StringUtils.isEmpty(station.getArea())) {
            doc.addField("station_area", station.getArea());
        }
        if (!StringUtils.isEmpty(station.getName())) {
            doc.addField("station_name", station.getName());
        }
        String posString = station.getLngE5() / 1E5 + " " + station.getLatE5() / 1E5;
        doc.addField("station_position", posString);
        //------------------------

        try {
            httpSolrClient.add(doc);
            httpSolrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StationResp> getOperatorStations(String passportId, String sort, String city, String keyword, RowBounds page) {
        List<StationResp> resps = new ArrayList<StationResp>();
        Operator operator = passportService.getOperatorByPassportId(passportId);
        List<Station> stations = new ArrayList<Station>();
        List<StationOrderPaymentVo> stationOrderPaymentVos = new ArrayList<StationOrderPaymentVo>();
        if (null != operator) {
            //运营
//            sort = null;
            stations = stationMapper.selectByOperatorId(operator.getId(), null, city, keyword, sort);
        } else {
            //物业
            Estate estate = passportService.getEstateByPassportId(passportId);
            stations = stationMapper.selectByOperatorId(null, estate.getId(), city, keyword, sort);
        }
        int amonut = 0;
        for (Station station : stations) {
            List<Order> orderList = orderService.listOrderByStationId(station.getId());
            StationResp resp = new StationResp(station);

            for (Order order : orderList) {
                if (!ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
                    amonut += order.getPayment();
                }
            }
            StationPortCount portCount = selfService.getPortCount(station.getId());
            if (portCount != null) {
                resp.setPortCount(portCount.getAllCount());
                resp.setWorkCount(portCount.getWorkingCount());
                resp.setFreeCount(portCount.getFreeCount());
            }
            resp.setAmount(amonut);
            resps.add(resp);
            amonut = 0;
        }
        //列表排序
        if ("amount-asc".equals(sort)) {
            Collections.sort(resps, new Comparator<StationResp>() {

                @Override
                public int compare(StationResp arg0, StationResp arg1) {
                    return arg0.getAmount().compareTo(arg1.getAmount());
                }
            });
        } else if ("amount-desc".equals(sort)) {
            Collections.sort(resps, new Comparator<StationResp>() {

                @Override
                public int compare(StationResp arg0, StationResp arg1) {
                    return arg1.getAmount().compareTo(arg0.getAmount());
                }
            });
        }
        return resps;
    }


    @Override
    public void addStationPort(StationPort stationPort) {
        stationPortMapper.insert(stationPort);
    }

    @Override
    public StationPort getPortById(String stationPortId) {
        return stationPortMapper.selectByPrimaryKey(stationPortId);
    }

    @Override
    public Result modifyStationPort(StationPort stationPort) {
        stationPortMapper.updateByPrimaryKeySelective(stationPort);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeStationPortId(String stationPortId) {
        //先查出 被删除 电口信息
        StationPort stationPort = stationPortMapper.selectByPrimaryKey(stationPortId);
        stationPortMapper.deleteByPrimaryKey(stationPortId);
        //获取 当下电站的 所有电口
        if (stationPort != null) {
            List<StationPort> stationPorts = stationPortMapper.selectStationPort(stationPort.getStationId());
            for (int i = 0; i < stationPorts.size(); i++) {
                StationPort stationPortOld = stationPorts.get(i);
                stationPortOld.setSeq(i);
                stationPortMapper.updateByPrimaryKeySelective(stationPortOld);
            }
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public int searchStationId(String stationId) {
        return stationPortMapper.selectStationId(stationId);
    }

    @Override
    public List<StationPort> searchStationList(String stationId) {
        return stationPortMapper.selectStationPort(stationId);
    }

    @Override
    public List<StationFaultVo> searchStationFaultVo(SearchStationFaultVo searchStationFaultVo, RowBounds page) {
        List<StationFaultVo> stationFaultVos = new ArrayList<StationFaultVo>();
        List<StationFault> stationFaults = stationFaultMapper.selectStationFaultVo(searchStationFaultVo, page);
        for (StationFault stationFault : stationFaults) {
            StationFaultVo stationFaultVo = new StationFaultVo(stationFault);
            stationFaultVos.add(stationFaultVo);
        }
        return stationFaultVos;
    }

    @Override
    public Result modifyStationFault(StationFault stationFault) {
        StationFault stationFaultOld = stationFaultMapper.selectByPrimaryKey(stationFault.getId());
        if (stationFaultOld == null) {
            return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
        }
        stationFaultOld.setBizDealResult(stationFault.getBizDealResult());
        stationFaultMapper.updateByPrimaryKeySelective(stationFaultOld);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeStationFault(String stationFaultId) {
        stationFaultMapper.deleteByPrimaryKey(stationFaultId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public StationFault getByStationFaultId(String stationFaultId) {
        StationFault stationFault = stationFaultMapper.selectByPrimaryKey(stationFaultId);
        stationFault.setIsViewed(true);
        stationFaultMapper.updateByPrimaryKeySelective(stationFault);
        return stationFault;
    }

    @Override
    public Result getStationDetail(String passportId, String stationId) {
        Station station = stationMapper.selectByPrimaryKey(stationId);
        String hm = DateUtils.getTimeIgnoreSecond(new Date());
        DateFormat df = new SimpleDateFormat("HH:mm");
        if (null == station) {
            return new Result(Code.ERROR, CodeMessage.STATION_NOT_EXIST);
        }
        OperatorStationResp operatorStationResp = new OperatorStationResp(station);
        //充电口
        List<StationPort> stationPorts = stationPortMapper.selectStationPort(stationId);
        List<StationPortResp> stationPortResps = new ArrayList<StationPortResp>();
        for (StationPort stationPort : stationPorts) {
            StationPortResp stationPortResp = new StationPortResp(stationPort);
            Order order = orderService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.RECHARGING.getValue());
            if (order != null) {
                stationPortResp.setRestTime(orderService.orderRestTime(order.getId()));//剩余时间
                stationPortResp.setPower(order.getPower());//功率
            } else {
                order = orderService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.WAITING_FOR_CONNECT.getValue());
            }
            Carowner carowner = passportService.getCarownerByPassportId(passportId);
            if (carowner != null && order != null && order.getCarownerId().equals(carowner.getId())) {
                stationPortResp.setOrderId(order.getId());
            }
            stationPortResps.add(stationPortResp);
        }
        //套餐
        List<ConsumePackageItemResp> consumePackageItemResps = new ArrayList<ConsumePackageItemResp>();
        List<ConsumePackageItem> consumePackageItems = consumePackageService.getItemsByConsumePackageId(station.getConsumePackageId());
        for (ConsumePackageItem consumePackageItem : consumePackageItems) {
            ConsumePackageItemResp consumePackageItemResp = new ConsumePackageItemResp(consumePackageItem);
            //套餐 时间 区间 判断
            try {
                if (df.parse(consumePackageItem.getStartTime()).getTime() < df.parse(hm).getTime() && df.parse(consumePackageItem.getEndTime()).getTime() > df.parse(hm).getTime()) {
                    consumePackageItemResps.add(consumePackageItemResp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return new Result(Code.ERROR);
            }
        }
        CarownerStationKey carownerStationKey = null;
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (carowner != null) {
            //用户收藏的电站
            carownerStationKey = carownerStationMapper.selectByCarOnerIdAndStationdId(carowner.getId(), stationId);
        }
        StationInfoResp info = new StationInfoResp(operatorStationResp, stationPortResps, consumePackageItemResps, carownerStationKey);
        //联系电话
        Estate estate = passportService.getByEstateId(station.getEstateId());
        if (null != estate) {
            info.setContactPhone(estate.getContactPhone());
        }
        return new Result(Code.SUCCESS, info);
    }

    @Override
    public List<Station> getAllStation() {
        return stationMapper.selectAll(Pagination.nullPage());
    }

    @Override
    public StationPortInfoVo getStationPortInfo(String stationId) {
        return stationPortMapper.selectStationPortInfo(stationId);
    }

    @Override
    public StationPort getStationPortByStationIdAndSeq(String stationId, int seq) {
        return stationPortMapper.selectByStationIdAndSeq(stationId, seq);
    }

    @Override
    public StationPortCount getPortCount(String stationId) {
        StationPortCount stationPortCount = new StationPortCount();
        int allCount = stationPortMapper.selectStationIdAndStatus(stationId, null);
        int workPortCount = stationPortMapper.selectStationIdAndStatus(stationId, new String[]{StationPortStatusEnum.WORKING.getValue(), StationPortStatusEnum.SUSPEND.getValue()});
        int freePortCount = stationPortMapper.selectStationIdAndStatus(stationId, new String[]{StationPortStatusEnum.FREE.getValue()});
        stationPortCount.setWorkingCount(workPortCount);
        stationPortCount.setFreeCount(freePortCount);
        stationPortCount.setAllCount(allCount);
        return stationPortCount;
    }

    @Override
    public void modifyStationPortStatusByStationId(String stationId, String status) {
        stationPortMapper.updateStationPortStatusByStationId(stationId, status);
    }

    @Override
    public StationPort getPortByQrcode(String qrCode) {
        return stationPortMapper.selectByQrcode(qrCode);
    }

    @Override
    public void addWarning(Warning warning) {
        if (warning.getType() == StationWarningEnum.TEMPERATURE_OVERRUN.getValue()) {
            warning.setRemark(Constants.TEMPERATURE_OVERRUN_REMARK);
        } else if (warning.getType() == StationWarningEnum.CHASSIS_OPENED.getValue()) {
            warning.setRemark(Constants.TEMPERATURE_CHASSIS_OPENED_REMARK);
        } else if (warning.getType() == StationWarningEnum.POWER_FAILURE.getValue()) {
            warning.setRemark(Constants.TEMPERATURE_POWER_FAILURE_REMARK);
        } else if (warning.getType() == StationWarningEnum.SMOKE_WARNING.getValue()) {
            warning.setRemark(Constants.TEMPERATURE_SMOKE_WARNING_REMARK);
        }
        warningMapper.insertSelective(warning);
    }

    @Override
    public void addStationRealTimeListen(StationRealTimeListen stationRealTimeListen) {
        stationRealTimeListenMapper.insertSelective(stationRealTimeListen);
    }

    @Override
    public void addStationPortRealTimeListen(StationPortRealTimeListen stationRealTimeListen) {
        stationPortRealTimeListenMapper.insertSelective(stationRealTimeListen);
    }

    @Override
    public List<StationRealTimeListen> getStationListen(String stationId, DateParam dateParam) {
        int maxCount = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.MAX_DATA_COUNT, Constants.DEFAULT_MAX_DATA_COUNT));
        double timeInterval = MathUtils.div((dateParam.getTimeEnd().getTime() - dateParam.getTimeStart().getTime()) / 1000, maxCount, 2);
        List<StationRealTimeListen> stationRealTimeListens = stationRealTimeListenMapper.selectByStationId(stationId, dateParam, timeInterval);
        return stationRealTimeListens;
    }

    @Override
    public List<StationPortRealTimeListen> getStationPortListen(String portId, DateParam dateParam) {
        int maxCount = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.MAX_DATA_COUNT, Constants.DEFAULT_MAX_DATA_COUNT));
        double timeInterval = MathUtils.div((dateParam.getTimeEnd().getTime() - dateParam.getTimeStart().getTime()) / 1000, maxCount, 2);
        List<StationPortRealTimeListen> stationPortRealTimeListenLists = stationPortRealTimeListenMapper.selectData(portId, dateParam, timeInterval);
        return stationPortRealTimeListenLists;
    }

    @Override
    public List<StationPortRealTimeListen> getOrderStationPortDetails(String portId) {
        List<StationPortRealTimeListen> stationPortRealTimeListenList = new ArrayList<>();
        for (int i = stationPortRealTimeListenMapper.selectByPortId(portId).size() - 1; i >= 0; i--) {
            stationPortRealTimeListenList.add(stationPortRealTimeListenMapper.selectByPortId(portId).get(i));
        }
        return stationPortRealTimeListenList;
    }

    @Override
    public List<StationPortRealTimeListen> getStationPortListenOrTime(String portId, Date olderCreateTime) {
        return stationPortRealTimeListenMapper.selectByPortIdOrTime(portId, olderCreateTime);
    }

    @Override
    public List<WarningVo> getWarningList(Passport passport, int reqType, int type, RowBounds page) {
        List<WarningVo> vos = new ArrayList<>();
        List<Warning> warnings = warningMapper.search(reqType, type, StationWarningEnum.TEMPERATURE_OVERRUN.getValue(), StationWarningEnum.SMOKE_WARNING.getValue(), Pagination.nullPage());
        for (Warning warning : warnings) {
            Station station = stationMapper.selectByPrimaryKey(warning.getStationId());
            if (null == station) {
                continue;
            }
            if (PassportTypeEnum.GOVERNMENT.getValue().equals(passport.getType())) {
                Government government = governmentService.getByPassportId(passport.getId());
                if (government != null && !government.getArea().substring(0, 13).equals(station.getArea().substring(0, 13))) {
                    continue;
                }
            }
            String operatorName = "";
            String estateName = "";
            Operator operator = operatorService.getById(station.getOperatorId());
            if (null != operator) {
                Passport operatorPassport = passportService.getById(operator.getPassportId());
                if (null != operatorPassport && !StringUtils.isEmpty(operatorPassport.getRealName())) {
                    operatorName = operatorPassport.getRealName();
                }
            }
            Estate estate = estateService.getById(station.getEstateId());
            if (null != estate) {
                Passport estatePassport = passportService.getById(estate.getPassportId());
                if (null != estatePassport && !StringUtils.isEmpty(estatePassport.getRealName())) {
                    estateName = estatePassport.getRealName();
                }
            }
            WarningVo vo = new WarningVo(warning, station, operatorName, estateName);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<WarningVo> searchWarningList(int reqType, int type, SearchStationVo searchStationVo, DateParam dateParam, RowBounds page) {
        searchStationVo.setAreaID(areaId(searchStationVo));
        return warningMapper.searchWarningKeyword(reqType, type, StationWarningEnum.TEMPERATURE_OVERRUN.getValue(), StationWarningEnum.SMOKE_WARNING.getValue(), searchStationVo, dateParam, page);
    }

    @Override
    public void updateStationSolr() {
        List<Station> stations = selfService.getAllStation();
        for (Station station : stations) {
            if (station.getLngE5() > 0 && station.getLatE5() > 0) {
                selfService.addToSolr(station);
            }
        }
    }

    @Override
    public StationPortRealTimeListen getStationPortRealTimeListenLastTimeByPortId(String portId) {
        StationPortRealTimeListen listen = new StationPortRealTimeListen();
        List<StationPortRealTimeListen> listens = stationPortRealTimeListenMapper.selectByPortId(portId);
        if (listens.size() > 0) {
            listen = listens.get(0);
        }
        return listen;
    }

    @Override
    public List<StationResp> passportUseStations(Carowner carowner, StationReq req) {
        List<StationResp> resps = new ArrayList<StationResp>();
        List<Order> orderList = orderService.searchOrderListCarOwnerId(carowner.getId(), OrderStatusEnum.RECHARGING.getValue());
        if (orderList.size() <= 0) {
            return resps;
        }
        String stationIds = "";
        int size = orderList.size();
        for (int i = 0; i < size; i++) {
            if (i != (size - 1)) {
                stationIds += "station_id:" + orderList.get(i).getStationId() + " OR ";
            } else {
                stationIds += "station_id:" + orderList.get(i).getStationId();
            }
        }

        SolrQuery params = new SolrQuery();
        String q_params = stationIds;
        params.set("q", q_params);
        params.set("pt", req.getLngE5() / 1E5 + " " + req.getLatE5() / 1E5); //当前经纬度
        params.set("fl", "*,dist:geodist(),score");//查询的结果中添加距离和score
        params.set("sort", "geodist() asc");  //根据距离排序：由近到远
        params.set("sfield", "station_position"); //经纬度的字段
        params.set("start", 0);  //记录开始位置
        params.set("rows", Integer.MAX_VALUE);  //查询的行数
        //
        List<StationBean> stationBeans = new ArrayList<StationBean>();
        try {
            QueryResponse queryResponse = httpSolrClient.query(params);
            stationBeans = queryResponse.getBeans(StationBean.class);
        } catch (Exception e) {
            log.error("获取附近店铺失败:{}", e.getMessage());
        }
        Iterator<StationBean> iterator = stationBeans.iterator();
        resps = getStationListResp(iterator);
        return resps;
    }

    @Override
    public List<StationMaintainLog> searchStationMaintainLogs(String stationId, RowBounds page) {
        return stationMaintainLogMapper.selectStationMaintainLog(stationId, page);
    }

    @Override
    public void addStationMaintainLog(StationMaintainLog stationMaintainLog) {
        stationMaintainLogMapper.insert(stationMaintainLog);
    }

    @Override
    public void modifyStationMaintainLog(StationMaintainLog stationMaintainLog) {
        stationMaintainLogMapper.updateByPrimaryKeySelective(stationMaintainLog);
    }

    @Override
    public StationMaintainLog getStationMaintainLogId(String stationMaintainLogId) {
        return stationMaintainLogMapper.selectByPrimaryKey(stationMaintainLogId);
    }

    @Override
    public void removeStationMaintainLogId(String stationMaintainLogId) {
        stationMaintainLogMapper.deleteByPrimaryKey(stationMaintainLogId);
    }

    @Override
    public int getStationCounts(String operatorId, String estateId) {
        return stationMapper.selectStationByOperatorIdAndEstateId(operatorId, estateId);
    }

    @Override
    public List<Station> getStationByEstateIdOrOperatorId(String operatorId, String estateId) {
        return stationMapper.selectStationByEstateIdOrOperatorId(operatorId, estateId);
    }

    @Override
    public void removeStationOrStationPort(List<Station> stations) {
        for (Station station : stations) {
            stationMapper.deleteByPrimaryKey(station.getId());
            //获取电站中所有电口
            List<StationPort> stationPorts = stationPortMapper.selectStationPort(station.getId());
            for (StationPort stationPort : stationPorts) {
                stationPortMapper.deleteByPrimaryKey(stationPort.getId());
            }
            //solr  删除
            try {
                httpSolrClient.deleteById(station.getId());
                httpSolrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<StationPort> getPortByStationIdAndStatus(String stationId, String[] statuses) {
        return stationPortMapper.selectStationPortByStationIdAndStatus(stationId, statuses);
    }

    @Override
    public List<StationPort> getAllPortsByStationId(String stationId) {
        return stationPortMapper.selectStationPort(stationId);
    }

    private List<StationResp> getStationListResp(Iterator<StationBean> iterator) {
        List<StationResp> resps = new ArrayList<StationResp>();
        while (iterator.hasNext()) {
            StationBean stationBean = iterator.next();
            Station station = stationMapper.selectByPrimaryKey(stationBean.getStationId());
            if (station == null || station.getIsDeleted() || station.getStatus() == StationStatusEnum.DISABLE.getValue()) {
                iterator.remove();
                continue;
            }

            String distan = "";
            if (stationBean.getDist() < 1) {
                distan = MathUtils.round(MathUtils.mul(stationBean.getDist(), 1000), 0) + "m";
            } else {
                distan = MathUtils.round(stationBean.getDist(), 2) + "km";
            }
            StationResp resp = new StationResp(station);
            StationPortCount portCount = selfService.getPortCount(station.getId());
            if (portCount != null) {
                resp.setPortCount(portCount.getAllCount());
                resp.setWorkCount(portCount.getWorkingCount());
                resp.setFreeCount(portCount.getFreeCount());
            }
            resp.setDistance(distan);
            resps.add(resp);
        }
        return resps;
    }

    /**
     * int 转 String
     *
     * @param seq
     * @return
     */
    private String getSeqString(int seq) {
//        if (seq <= 9) {
//            return "0" + seq;
//        }
        return String.valueOf(seq);
    }

    /**
     * 电站 口添加
     *
     * @param seq
     * @param station
     */
    private void addStationPort(int seq, Station station) {
        //获取总共充电口数量
        StationPort stationPort = new StationPort();
        stationPort.setId(UUIDUtils.createId());
        stationPort.setStationId(station.getId());
        stationPort.setSeq(seq);
        stationPort.setNumber(station.getNumber() + "-" + getSeqString(seq + 1));
        stationPort.setQrCode(station.getNumber() + "-" + getSeqString(seq + 1));
        stationPort.setMaxPower(Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.MAX_POWER, "400")));
        stationPort.setMinPower(Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.MIN_POWER, "20")));
        stationPort.setTrickleTime(Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.TRICKLE_TIME, "15")));
        stationPort.setStatus(StationPortStatusEnum.FAULT.getValue());
        stationPort.setIsAutoStop("1".equals(commonService.getConfigValueByName(SystemParamKeys.IS_AUTO, "1")) ? true : false);
        stationPort.setIsLargePower("1".equals(commonService.getConfigValueByName(SystemParamKeys.IS_LARGE, "1")) ? true : false);
        stationPort.setIsEnabled(true);
        stationPort.setIsDeleted(false);
        stationPort.setCreationTime(new Date());
        stationPort.setCreator(station.getCreator());
        stationPortMapper.insert(stationPort);
    }

    /**
     * 地址 like 查询使用
     *
     * @param searchStationVo
     * @return
     */
    public String areaId(SearchStationVo searchStationVo) {
        String areaId = "";
        if (!StringUtils.isEmpty(searchStationVo.getSearchProvince())) {
            areaId += searchStationVo.getSearchProvince() + ",";
            if (!StringUtils.isEmpty(searchStationVo.getSearchCity())) {
                areaId += searchStationVo.getSearchCity() + ",";
                if (!StringUtils.isEmpty(searchStationVo.getSearchTown())) {
                    areaId += searchStationVo.getSearchTown();
                }
            }
        }
        return areaId;
    }
}
