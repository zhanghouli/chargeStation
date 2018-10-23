package com.jopool.chargingStation;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gexin on 2017/9/9.
 */
public class CustomMqttTestCallback implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(CustomMqttTestCallback.class);
    private MqttClientService mqttService;

    public CustomMqttTestCallback() {
    }

    public CustomMqttTestCallback(MqttClientService mqttService) {
        this.mqttService = mqttService;
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
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("mqtt receive msg : {},{}", topic, JSON.toJSONString(message));
    }
}
