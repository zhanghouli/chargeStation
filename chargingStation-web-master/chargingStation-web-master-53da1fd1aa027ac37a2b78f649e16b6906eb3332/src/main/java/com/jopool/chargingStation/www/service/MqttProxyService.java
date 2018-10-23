package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.StationPort;
import com.jopool.chargingStation.www.mqtt.messages.*;

/**
 * Created by gexin on 2017/9/9.
 */
public interface MqttProxyService {
    /**
     * 发布开关协议
     *
     * @param stationPort
     * @param switchStatus
     * @param openTime
     */
    void pubSetMessage(StationPort stationPort, boolean switchStatus, int openTime, boolean wait);

    /**
     * 发布开关协议
     *
     * @param setMessage
     */
    void pubSetMessage(SetMessage setMessage);

    /**
     * 收到上线协议
     *
     * @param onlineMessage
     */
    void onReceivedOnlineMessage(OnlineMessage onlineMessage);

    /**
     * 收到遗嘱协议
     *
     * @param willMessage
     */
    void onReceivedWillMessage(WillMessage willMessage);

    /**
     * 收到状态协议
     *
     * @param stateMessage
     */
    void onReceivedStateMessage(StateMessage stateMessage);

    /**
     * 收到充电完成协议
     *
     * @param reportMessage
     */
    void onReceivedReportMessage(ReportMessage reportMessage);

    /**
     * 收到充电箱异常协议
     *
     * @param warningStationMessage
     */
    void onReceivedWarningStationMessage(WarningStationMessage warningStationMessage);

    /**
     * 收到充电口异常协议
     * 弃用
     *
     * @param warningAdapterMessage
     */
    void onReceivedWarningAdapterMessage(WarningAdapterMessage warningAdapterMessage);
}
