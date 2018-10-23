package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationRealTimeListen;
import com.jopool.chargingStation.www.vo.common.DateParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRealTimeListenMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationRealTimeListen record);

    int insertSelective(StationRealTimeListen record);

    StationRealTimeListen selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationRealTimeListen record);

    int updateByPrimaryKey(StationRealTimeListen record);

    /**
     * select by stationId
     * 获取电站历史数据
     *
     * @param stationId
     * @return
     */
    List<StationRealTimeListen> selectByStationId(@Param("stationId")String stationId, @Param("dateParam") DateParam dateParam, @Param("timeInterval") double timeInterval);
}