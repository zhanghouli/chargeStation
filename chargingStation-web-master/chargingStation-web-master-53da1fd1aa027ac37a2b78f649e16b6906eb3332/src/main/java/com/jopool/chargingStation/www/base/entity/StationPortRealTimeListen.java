package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.mqtt.messages.StateMessage;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class StationPortRealTimeListen {
    private String id;

    private String stationId;

    private String stationPortId;

    private Integer stationPortSeq;

    private String current;

    private String power;

    private String energy;

    private Integer remainingTime;

    private int status;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    public StationPortRealTimeListen() {
    }

    public StationPortRealTimeListen(Station station, StationPort port, StateMessage.St st) {
        this.id = UUIDUtils.createId();
        this.stationId = station.getId();
        this.stationPortId = port.getId();
        this.stationPortSeq = port.getSeq();
        this.current = st.getCur() + "";
        this.power = st.getPow() + "";
        this.energy = st.getEn() + "";
        this.remainingTime = st.getTl();
        this.status = st.getSst();
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

    public String getStationPortId() {
        return stationPortId;
    }

    public void setStationPortId(String stationPortId) {
        this.stationPortId = stationPortId;
    }

    public Integer getStationPortSeq() {
        return stationPortSeq;
    }

    public void setStationPortSeq(Integer stationPortSeq) {
        this.stationPortSeq = stationPortSeq;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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