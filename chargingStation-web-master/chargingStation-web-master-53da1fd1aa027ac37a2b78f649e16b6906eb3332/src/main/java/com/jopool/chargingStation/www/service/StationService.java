package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.request.ModifyStationPortReq;
import com.jopool.chargingStation.www.request.ModifyStationReq;
import com.jopool.chargingStation.www.request.StationReq;
import com.jopool.chargingStation.www.response.StationPortCount;
import com.jopool.chargingStation.www.response.StationResp;
import com.jopool.chargingStation.www.vo.StationFaultVo;
import com.jopool.chargingStation.www.vo.StationPortInfoVo;
import com.jopool.chargingStation.www.vo.StationVo;
import com.jopool.chargingStation.www.vo.WarningVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchStationFaultVo;
import com.jopool.chargingStation.www.vo.common.SearchStationVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/8/29.
 */
public interface StationService {
    /**
     * 根据id获取单个
     *
     * @param stationId
     */
    Station getById(String stationId);

    /**
     * 根据number获取单个
     *
     * @param number
     */
    Station getByNumber(String number);

    /**
     * 添加
     *
     * @param station
     */
    void addStation(Station station);

    /**
     * 修改
     *
     * @param station
     * @return
     */
    Result modifyStation(Station station);

    /**
     * 删除
     *
     * @param stationId
     * @return
     */
    Result removeStationById(String stationId);

    /**
     * 获得电站信息
     *
     * @param stationId
     * @return
     */
    Station searchByStationId(String stationId);

    /**
     * 分页 查询
     *
     * @param stationVo
     * @param page
     * @return
     */
    List<StationVo> searchStationVo(SearchStationVo stationVo, RowBounds page);


    /**
     * 新增时 充电站 根据充电口数量 添加充电口
     *
     * @param station
     */
    void addStationPortAccordingToPort(Station station);

    /**
     * 修改充电站 充电口数量 时改变 充电口
     *
     * @param station
     */
    void modifyStationPortAccordingToPort(Station station);

    /**
     * 添加 充电站 电口
     *
     * @param stationPort
     */
    void addStationPort(StationPort stationPort);

    /**
     * 查询 单挑 数据
     *
     * @param stationPortId
     * @return
     */
    StationPort getPortById(String stationPortId);

    /**
     * 修改  充电口
     *
     * @param stationPort
     * @return
     */
    Result modifyStationPort(StationPort stationPort);

    /**
     * 删除 电站 电口
     *
     * @param stationPortId
     * @return
     */
    Result removeStationPortId(String stationPortId);

    /**
     * 查找 充电站  充电口
     *
     * @param stationId
     * @return
     */
    int searchStationId(String stationId);

    /**
     * 充电站 id  查询 电口数组
     *
     * @param stationId
     * @return
     */
    List<StationPort> searchStationList(String stationId);

    /**
     * 收藏电站
     *
     * @param passportId
     * @param stationId
     * @return
     */
    Result collectStation(String passportId, String stationId);

    /**
     * 取消收藏
     *
     * @param passportId
     * @param stationId
     * @return
     */
    Result cancelCollectStation(String passportId, String stationId);

    /**
     * 扫码充电
     *
     * @param passportId
     * @param portId
     * @param payType
     * @return
     */
    Result startRecharge(String passportId, String portId, String consumePackageItemId, String payType, String chargeType);

    /**
     * 添加订单快照
     *
     * @param order
     * @param station
     */
    String addOrderStationSnapshot(Order order, Station station);

    /**
     * 添加订单充电口快照
     *
     * @param order
     * @param stationPort
     * @return
     */
    StationPortSnapshot addOrderStationPortSnapshot(Order order, StationPort stationPort);

    /**
     * 修改充电站设置
     *
     * @param passportId
     * @param req
     * @return
     */
    Result modifyStationSetUp(String passportId, ModifyStationReq req);

    /**
     * 修改充点口设置
     *
     * @param passportId
     * @param req
     * @return
     */
    Result modifyStationPortSetUp(String passportId, ModifyStationPortReq req);

    /**
     * 获取收藏电站
     *
     * @param lngE5
     * @param latE5
     * @param page
     * @return
     */
    List<StationResp> getMyStations(String passportId, int lngE5, int latE5, RowBounds page);

    /**
     * 附近电站
     *
     * @param req
     * @param page
     * @return
     */
    List<StationResp> getNearByStations(StationReq req, int page);

    void addToSolr(Station station);

    /**
     * 运营商/物业充电站列表
     *
     * @param passportId
     * @param sort
     * @param keyword
     * @param page
     * @return
     */
    List<StationResp> getOperatorStations(String passportId, String sort, String city, String keyword, RowBounds page);


    /**
     * 故障 电站 分页 查询
     *
     * @param searchStationFaultVo
     * @param page
     * @return
     */
    List<StationFaultVo> searchStationFaultVo(SearchStationFaultVo searchStationFaultVo, RowBounds page);


    /**
     * 故障 电站 处理 结果提交
     *
     * @param stationFault
     * @return
     */
    Result modifyStationFault(StationFault stationFault);

    /**
     * 删除 故障 电站 信息
     *
     * @param stationFaultId
     * @return
     */
    Result removeStationFault(String stationFaultId);

    /**
     * 单条 故障 电站 信息
     *
     * @param stationFaultId
     * @return
     */
    StationFault getByStationFaultId(String stationFaultId);

    /**
     * 充电站详情
     *
     * @param passportId
     * @param stationId
     * @return
     */
    Result getStationDetail(String passportId, String stationId) throws ParseException;

    /**
     * 获取所有电站
     *
     * @return
     */
    List<Station> getAllStation();

    /**
     * 获取 电口 详情
     *
     * @param stationId
     * @return
     */
    StationPortInfoVo getStationPortInfo(String stationId);

    /**
     * 根据充电站id和充电口seq获取充电口
     *
     * @param stationId
     * @param seq
     * @return
     */
    StationPort getStationPortByStationIdAndSeq(String stationId, int seq);

    /**
     * 获取充电站接口状态
     *
     * @param stationId
     * @return
     */
    StationPortCount getPortCount(String stationId);

    /**
     * 修改充电口状态
     *
     * @param id
     * @param value
     */
    void modifyStationPortStatusByStationId(String id, String value);

    /**
     * 根据二维码获取宠溺单口
     *
     * @param qrCode
     * @return
     */
    StationPort getPortByQrcode(String qrCode);

    /**
     * 添加警告
     *
     * @param warning
     */
    void addWarning(Warning warning);

    /**
     * 添加充电站监听记录
     *
     * @param stationRealTimeListen
     */
    void addStationRealTimeListen(StationRealTimeListen stationRealTimeListen);

    /**
     * 添加充电口监听记录
     *
     * @param stationRealTimeListen
     */
    void addStationPortRealTimeListen(StationPortRealTimeListen stationRealTimeListen);

    /**
     * 获取充电站监听记录
     *
     * @param stationId
     * @param dateParam
     * @return
     */
    List<StationRealTimeListen> getStationListen(String stationId, DateParam dateParam);

    /**
     * 获取电口历史数据
     *
     * @param portId
     * @param dateParam
     * @return
     */
    List<StationPortRealTimeListen> getStationPortListen(String portId, DateParam dateParam);


    /**
     * 获取电口历史数据-->订单详情
     *
     * @param portId
     * @return
     */
    List<StationPortRealTimeListen> getOrderStationPortDetails(String portId);

    /**
     * 订单创建时间  查找 电口 电流信息
     *
     * @param portId
     * @param olderCreateTime
     * @return
     */
    List<StationPortRealTimeListen> getStationPortListenOrTime(String portId, Date olderCreateTime);

    /**
     * 获取店站警告
     *
     * @param type
     * @param page
     * @return
     */
    List<WarningVo> getWarningList(Passport passport, int reqType, int type, RowBounds page);


    /**
     * @param reqType
     * @param type
     * @param page
     * @return
     */
    List<WarningVo> searchWarningList(int reqType, int type, SearchStationVo searchStationVo, DateParam dateParam, RowBounds page);

    /**
     * update station solr
     */
    void updateStationSolr();

    /**
     * 获取最新listen数据
     * 计算
     *
     * @param portId
     * @return
     */
    StationPortRealTimeListen getStationPortRealTimeListenLastTimeByPortId(String portId);

    /**
     * 用户使用中的电站
     *
     * @param carowner
     * @return
     */
    List<StationResp> passportUseStations(Carowner carowner, StationReq req);


    /**
     * 维护日志分页查询
     *
     * @param stationId
     * @param page
     * @return
     */
    List<StationMaintainLog> searchStationMaintainLogs(String stationId, RowBounds page);

    /**
     * 维护日志添加
     *
     * @param stationMaintainLog
     */
    void addStationMaintainLog(StationMaintainLog stationMaintainLog);


    /**
     * 修改
     *
     * @param stationMaintainLog
     */
    void modifyStationMaintainLog(StationMaintainLog stationMaintainLog);

    /**
     * id 查找维护日志
     *
     * @param stationMaintainLogId
     * @return
     */
    StationMaintainLog getStationMaintainLogId(String stationMaintainLogId);

    /**
     * 维护日志删除
     *
     * @param stationMaintainLogId
     */
    void removeStationMaintainLogId(String stationMaintainLogId);

    /**
     * 运营商 物业 id 查找 电站 数量
     *
     * @param operatorId
     * @param estateId
     * @return
     */
    int getStationCounts(String operatorId, String estateId);

    /**
     * get station by EstateIdOrOperatorId
     *
     * @param operatorId
     * @param estateId
     * @return
     */
    List<Station> getStationByEstateIdOrOperatorId(String operatorId, String estateId);

    /**
     * 电站删除 关联 电口也删除
     *
     * @param stations
     */
    void removeStationOrStationPort(List<Station> stations);

    /**
     * get port by stationId and status
     *
     * @param stationId
     * @param statuses
     * @return
     */
    List<StationPort> getPortByStationIdAndStatus(String stationId, String[] statuses);

    /**
     * get all ports
     * @param stationId
     * @return
     */
    List<StationPort> getAllPortsByStationId(String stationId);
}
