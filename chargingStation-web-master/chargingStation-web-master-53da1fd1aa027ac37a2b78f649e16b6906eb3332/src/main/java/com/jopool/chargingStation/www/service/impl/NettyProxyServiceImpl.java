package com.jopool.chargingStation.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.enums.DeviceStatusEnum;
import com.jopool.chargingStation.www.netty.vo.Session;
import com.jopool.chargingStation.www.netty.vo.req.HeartbeatMsg;
import com.jopool.chargingStation.www.netty.vo.req.LocationMsg;
import com.jopool.chargingStation.www.service.LocationService;
import com.jopool.chargingStation.www.service.NettyProxyService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.task.BoundsConsumerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gexin on 2017/10/24.
 */
@Service
public class NettyProxyServiceImpl extends BaseMsgProcessService implements NettyProxyService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private LocationService locationService;
    @Resource
    private PassportService passportService;

    @Override
    public void processHeartbeatMsg(HeartbeatMsg msg) throws Exception {
        log.debug("心跳信息:{}", JSON.toJSONString(msg, true));
        final String sessionId = Session.buildId(msg.getChannel());
        Session session = sessionManager.findBySessionId(sessionId);
        if (session == null) {
            session = Session.buildSession(msg.getChannel(), msg.getMsgHeader().getDeviceNumber());
        }
        session.setAuthenticated(true);
        session.setDeviceNumber(msg.getMsgHeader().getDeviceNumber());
        sessionManager.put(session.getId(), session);
        //更新状态
        Carowner carowner = passportService.getCarownerByDeviceNumber(msg.getMsgHeader().getDeviceNumber());
        if (null != carowner && !DeviceStatusEnum.ONLINE.getValue().equals(carowner.getDeviceStatus())) {
            carowner.setDeviceStatus(DeviceStatusEnum.ONLINE.getValue());
            passportService.modifyCarOwner(carowner);
        }
    }

    @Override
    public void processLocationMsg(LocationMsg msg) throws Exception {
        BoundsConsumerTask.trackQueue.push(msg);
    }

    @Override
    public void processInactive(String deviceNumber) {
        Carowner carowner = passportService.getCarownerByDeviceNumber(deviceNumber);
        if (null != carowner && !DeviceStatusEnum.OFFLINE.getValue().equals(carowner.getDeviceStatus())) {
            carowner.setDeviceStatus(DeviceStatusEnum.OFFLINE.getValue());
            passportService.modifyCarOwner(carowner);
        }
    }
}
