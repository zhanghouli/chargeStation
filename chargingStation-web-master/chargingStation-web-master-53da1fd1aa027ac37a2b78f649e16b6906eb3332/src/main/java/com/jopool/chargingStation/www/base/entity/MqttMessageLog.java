package com.jopool.chargingStation.www.base.entity;

import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class MqttMessageLog {
    private String id;

    private String number;

    private String topic;

    private String message;

    private Date creationTime;

    public MqttMessageLog() {
    }

    public MqttMessageLog(String did, String topic, String message) {
        this.id = UUIDUtils.createId();
        this.number = did;
        this.topic = topic;
        this.message = message;
        this.creationTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}