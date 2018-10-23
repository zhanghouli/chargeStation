package com.jopool.chargingStation.www.mqtt.callback;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.base.entity.MqttMessageLog;
import com.jopool.chargingStation.www.mqtt.messages.*;
import com.jopool.chargingStation.www.service.MqttProxyService;
import com.jopool.chargingStation.www.service.task.MqttMessageLogTask;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gexin on 2017/9/9.
 */
public class CustomMqttCallback implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(CustomMqttCallback.class);
    private MqttProxyService mqttProxyService;

    public CustomMqttCallback() {
    }

    public CustomMqttCallback(MqttProxyService mqttProxyService) {
        this.mqttProxyService = mqttProxyService;
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.info("mqtt reconnect : {}", cause.getMessage());
//        mqttService.reconnect();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String msg = new String(message.getPayload());
            log.info("mqtt receive msg : {},{}", topic, msg);
            String did = JSON.parseObject(msg).getString("did");
            MqttMessageLogTask.msgQueue.push(new MqttMessageLog(did, topic, msg));
            if (topic.contains("online")) {
                //硬件上线协议
                OnlineMessage onlineMessage = JSON.parseObject(msg, OnlineMessage.class);
                mqttProxyService.onReceivedOnlineMessage(onlineMessage);
            } else if (topic.contains("will")) {
                //遗嘱协议
                WillMessage willMessage = JSON.parseObject(msg, WillMessage.class);
                mqttProxyService.onReceivedWillMessage(willMessage);
            } else if (topic.contains("state")) {
                //状态协议
                StateMessage stateMessage = JSON.parseObject(msg, StateMessage.class);
                mqttProxyService.onReceivedStateMessage(stateMessage);
            } else if (topic.contains("report")) {
                //充电完成协议
                ReportMessage reportMessage = JSON.parseObject(msg, ReportMessage.class);
                mqttProxyService.onReceivedReportMessage(reportMessage);
            } else if (topic.contains("warning") && topic.contains("station")) {
                //充电箱异常协议
                WarningStationMessage warningStationMessage = JSON.parseObject(msg, WarningStationMessage.class);
                mqttProxyService.onReceivedWarningStationMessage(warningStationMessage);
            } else if (topic.contains("warning") && topic.contains("adapter")) {
                //充电口异常协议
                WarningAdapterMessage warningAdapterMessage = JSON.parseObject(msg, WarningAdapterMessage.class);
                mqttProxyService.onReceivedWarningAdapterMessage(warningAdapterMessage);
            }
        } catch (Exception e) {
            log.info("mqtt error : {}", e.getLocalizedMessage());
        }
    }
}
