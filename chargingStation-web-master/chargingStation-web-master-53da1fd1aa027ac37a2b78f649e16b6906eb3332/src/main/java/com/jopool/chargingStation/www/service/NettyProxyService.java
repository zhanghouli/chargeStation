package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.netty.vo.req.HeartbeatMsg;
import com.jopool.chargingStation.www.netty.vo.req.LocationMsg;

/**
 * Created by gexin on 2017/10/24.
 */
public interface NettyProxyService {
    /**
     * 心跳
     *
     * @param msg
     * @throws Exception
     */
    void processHeartbeatMsg(HeartbeatMsg msg) throws Exception;

    /**
     * 上传位置
     *
     * @param msg
     * @throws Exception
     */
    void processLocationMsg(LocationMsg msg) throws Exception;

    /**
     * 离线
     *
     * @param deviceNumber
     */
    void processInactive(String deviceNumber);
}
