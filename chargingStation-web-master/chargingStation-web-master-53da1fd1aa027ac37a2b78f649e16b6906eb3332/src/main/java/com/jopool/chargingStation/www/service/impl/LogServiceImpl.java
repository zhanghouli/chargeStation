package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.MqttMessageLog;
import com.jopool.chargingStation.www.dao.MqttMessageLogMapper;
import com.jopool.chargingStation.www.service.LogService;
import com.jopool.jweb.spring.SelfBeanAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gexin
 * @date 2018/4/6
 */
@Service
public class LogServiceImpl extends BaseServiceImpl implements LogService, SelfBeanAware<LogService> {
    private LogService           logService;
    @Resource
    private MqttMessageLogMapper mqttMessageLogMapper;

    @Override
    public void addMqttLog(String did, String topic, String message) {
        MqttMessageLog messageLog = new MqttMessageLog(did, topic, message);
        logService.addMqttLog(messageLog);
    }

    @Override
    public void addMqttLog(MqttMessageLog msg) {
        mqttMessageLogMapper.insert(msg);
    }

    @Override
    public void setSelfBean(LogService object) {
        this.logService = object;
    }
}
