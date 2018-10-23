package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationPortRealTimeListen;
import com.jopool.chargingStation.www.vo.common.DateParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StationPortRealTimeListenMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationPortRealTimeListen record);

    int insertSelective(StationPortRealTimeListen record);

    StationPortRealTimeListen selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationPortRealTimeListen record);

    int updateByPrimaryKey(StationPortRealTimeListen record);

    /**
     * select by portId
     *
     * @param stationPortId
     * @return
     */
    List<StationPortRealTimeListen> selectByPortId(String stationPortId);

    /**
     * @param portId
     * @param orderCreateTime
     * @return
     */
    List<StationPortRealTimeListen> selectByPortIdOrTime(@Param("portId") String portId, @Param("orderCreateTime") Date orderCreateTime);

    /**
     * 历史数据采样
     *
     * @param stationPortId
     * @param dateParam
     * @param timeInterval
     * @return
     */
    List<StationPortRealTimeListen> selectData(@Param("stationPortId") String stationPortId, @Param("dateParam") DateParam dateParam, @Param("timeInterval") double timeInterval);
}