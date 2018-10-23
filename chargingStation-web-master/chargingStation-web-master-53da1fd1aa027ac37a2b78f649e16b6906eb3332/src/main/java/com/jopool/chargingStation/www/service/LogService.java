package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.MqttMessageLog;

/**
 * Created by gexin on 2018/4/6.
 */
public interface LogService {
    void addMqttLog(String did, String topic, String message);

    void addMqttLog(MqttMessageLog msg);
}
