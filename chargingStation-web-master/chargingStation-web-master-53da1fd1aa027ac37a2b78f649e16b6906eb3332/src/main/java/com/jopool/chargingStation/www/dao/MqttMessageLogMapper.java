package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.MqttMessageLog;

public interface MqttMessageLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(MqttMessageLog record);

    int insertSelective(MqttMessageLog record);

    MqttMessageLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MqttMessageLog record);

    int updateByPrimaryKey(MqttMessageLog record);
}