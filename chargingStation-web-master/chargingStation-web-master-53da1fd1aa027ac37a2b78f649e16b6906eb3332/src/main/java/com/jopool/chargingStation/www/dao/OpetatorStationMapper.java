package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.OpetatorStationKey;
import org.springframework.stereotype.Repository;

@Repository
public interface OpetatorStationMapper {
    int deleteByPrimaryKey(OpetatorStationKey key);

    int insert(OpetatorStationKey record);

    int insertSelective(OpetatorStationKey record);
}