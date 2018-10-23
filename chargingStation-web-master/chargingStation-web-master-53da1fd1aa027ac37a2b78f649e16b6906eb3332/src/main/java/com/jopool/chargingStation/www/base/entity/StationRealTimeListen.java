package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.mqtt.messages.StateMessage;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class StationRealTimeListen {
    private String id;

    private String stationId;

    private String temperature;

    private String voltage;

    private String energy;

    private String version;

    private Date dataTime;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    public StationRealTimeListen() {
    }

    public StationRealTimeListen(Station station, StateMessage stateMessage, Date dataTime) {
        this.id = UUIDUtils.createId();
        this.stationId = station.getId();
        this.temperature = stateMessage.getTemp() + "";
        this.voltage = stateMessage.getVol() + "";
        this.energy = stateMessage.getEn() + "";
        this.version = stateMessage.getV();
        this.dataTime = dataTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}