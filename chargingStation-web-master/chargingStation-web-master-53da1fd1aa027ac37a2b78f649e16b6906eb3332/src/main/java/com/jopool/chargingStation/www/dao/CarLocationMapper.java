package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CarLocation;

public interface CarLocationMapper {
    int deleteByPrimaryKey(String id);

    int insert(CarLocation record);

    int insertSelective(CarLocation record);

    CarLocation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CarLocation record);

    int updateByPrimaryKey(CarLocation record);
}