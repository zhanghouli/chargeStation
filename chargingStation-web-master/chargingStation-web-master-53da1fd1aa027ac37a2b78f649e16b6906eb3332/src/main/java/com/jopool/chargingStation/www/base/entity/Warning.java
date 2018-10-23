package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.mqtt.messages.WarningStationMessage;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;
import java.util.UUID;

public class Warning {
    private String id;

    private String stationId;

    private Date dataTime;

    private String temperature;

    private String remark;

    private int type;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    public Warning() {
    }

    public Warning(WarningStationMessage warning, Station station, Date date) {
        this.id = UUIDUtils.createId();
        this.stationId = station.getId();
        this.dataTime = date;
        this.temperature = warning.getTemp() + "";
        this.type = warning.getWarning();
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

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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