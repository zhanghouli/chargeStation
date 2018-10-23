package com.jopool.chargingStation;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.mqtt.messages.WillMessage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * mqtt服务，用户订阅消息，发布消息
 * Created by gexin on 2017/9/9.
 */
public class MqttClientService {
    private static final String DID = "TST00000001";
    private static final Logger log = LoggerFactory.getLogger(MqttClientService.class);
    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        connect();
    }

    @PreDestroy
    public void destory() {

    }

    /**
     * 连接服务器
     */
    private void connect() {
        try {
            mqttClient = new MqttClient(ApplicationConfigHelper.getMqttUrl(), ApplicationConfigHelper.getMqttClientId() + "-client-1", new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(ApplicationConfigHelper.getMqttUserName());
            // 设置连接的密码
            options.setPassword(ApplicationConfigHelper.getMqttPassword().toCharArray());
            // 设置超时时间
            options.setConnectionTimeout(10);
            // 设置自动重连
            options.setAutomaticReconnect(true);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill("dc/cs/will/" + DID, JSON.toJSONBytes(new WillMessage(DID, new Date().getTime() / 1000)), 2, false);
            //
            mqttClient.setCallback(new CustomMqttTestCallback(this));
            mqttClient.connect(options);
            //订阅消息
            subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重连
     */
    public void reconnect() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布消息
     *
     * @param topicName
     * @param obj
     */
    public void publish(String topicName, Object obj) {
        String message = JSON.toJSONString(obj);
        publish(topicName, message);
    }

    /**
     * 发布消息
     *
     * @param topicName
     * @param payload
     */
    public void publish(String topicName, String payload) {
        MqttMessage message = new MqttMessage();
        message.setQos(2);
        message.setRetained(false);
        message.setPayload(payload.getBytes());
        publish(topicName, message);
    }

    /**
     * 发布消息
     *
     * @param topicName
     * @param message
     */
    public void publish(String topicName, MqttMessage message) {
        try {
            MqttTopic topic = mqttClient.getTopic(topicName);
            MqttDeliveryToken token = topic.publish(message);
            token.waitForCompletion();
            log.info("mqtt published msg: {},{},{},{}", topicName, JSON.toJSONString(message), new String(message.getPayload()), token.isComplete());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅消息消息
     */
    public void subscribe() {
        try {
            mqttClient.subscribe(new String[]{"dc/cs/param/" + DID, "dc/cs/set/" + DID}, new int[]{2, 2});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
