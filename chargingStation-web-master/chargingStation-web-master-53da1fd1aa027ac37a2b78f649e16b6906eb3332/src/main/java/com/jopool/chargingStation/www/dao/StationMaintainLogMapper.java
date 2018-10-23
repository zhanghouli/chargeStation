package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationMaintainLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StationMaintainLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationMaintainLog record);

    int insertSelective(StationMaintainLog record);

    StationMaintainLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationMaintainLog record);

    int updateByPrimaryKey(StationMaintainLog record);

    /**
     * @param rowBounds
     * @return
     */
    List<StationMaintainLog> selectStationMaintainLog(@Param("stationId") String stationId, RowBounds rowBounds);
}