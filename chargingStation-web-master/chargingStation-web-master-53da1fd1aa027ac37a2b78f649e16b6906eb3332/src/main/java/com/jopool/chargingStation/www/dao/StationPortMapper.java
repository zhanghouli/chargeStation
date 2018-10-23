package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationPort;

import com.jopool.chargingStation.www.vo.StationPortInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationPortMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationPort record);

    int insertSelective(StationPort record);

    StationPort selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationPort record);

    int updateByPrimaryKey(StationPort record);

    /**
     * 查找 充电口 数量
     *
     * @param stationId
     * @return
     */
    int selectStationId(@Param("stationId") String stationId);

    /**
     * @param stationId
     * @return
     */
    List<StationPort> selectStationPort(@Param("stationId") String stationId);

    /**
     * 单个接口详情
     *
     * @param stationId
     * @return
     */
    StationPortInfoVo selectStationPortInfo(@Param("stationId") String stationId);

    /**
     * 获取状态数量
     *
     * @param stationId
     * @param status
     * @return
     */
    int selectStationIdAndStatus(@Param("stationId") String stationId, @Param("status") String[] status);

    /**
     * 获取状态 电口
     *
     * @param stationId
     * @param status
     * @return
     */
    List<StationPort> selectStationPortByStationIdAndStatus(@Param("stationId") String stationId, @Param("status") String[] status);

    /**
     * 根据充电站id和充电口seq获取充电口
     *
     * @param stationId
     * @param seq
     * @return
     */
    StationPort selectByStationIdAndSeq(@Param("stationId") String stationId, @Param("seq") int seq);

    /**
     * 修改充电口状态
     *
     * @param stationId
     * @param status
     */
    void updateStationPortStatusByStationId(@Param("stationId") String stationId, @Param("status") String status);

    /**
     * 根据qrcode获取充电口
     *
     * @param qrCode
     * @return
     */
    StationPort selectByQrcode(@Param("qrCode") String qrCode);


}