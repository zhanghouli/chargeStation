package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CarownerStationKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarownerStationMapper {
    int deleteByPrimaryKey(CarownerStationKey key);

    int insert(CarownerStationKey record);

    int insertSelective(CarownerStationKey record);

    /**
     * 删除收藏
     *
     * @param carownerId
     * @param stationId
     * @return
     */
    int deleteByCarownerAndStationId(@Param("carownerId") String carownerId, @Param("stationId") String stationId);

    /**
     * 用户收藏
     *
     * @param carownerId
     * @return
     */
    List<CarownerStationKey> selectByCarownerId(String carownerId, RowBounds page);

    /**
     * 查询 是否 存在 防止重复
     * @param carownerId
     * @param stationId
     * @return
     */
    CarownerStationKey  selectByCarOnerIdAndStationdId(@Param("carownerId") String carownerId, @Param("stationId") String stationId);
}