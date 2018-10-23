package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class StationPort {
    private String id;

    private String stationId;

    private Integer seq;

    private String number;

    private String qrCode;

    private String status;

    private Integer maxPower;

    private Integer minPower;

    private Integer trickleTime;

    private Boolean isAutoStop;

    private Boolean isLargePower;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Integer maxPower) {
        this.maxPower = maxPower;
    }

    public Integer getMinPower() {
        return minPower;
    }

    public void setMinPower(Integer minPower) {
        this.minPower = minPower;
    }

    public Integer getTrickleTime() {
        return trickleTime;
    }

    public void setTrickleTime(Integer trickleTime) {
        this.trickleTime = trickleTime;
    }

    public Boolean getIsAutoStop() {
        return isAutoStop;
    }

    public void setIsAutoStop(Boolean isAutoStop) {
        this.isAutoStop = isAutoStop;
    }

    public Boolean getIsLargePower() {
        return isLargePower;
    }

    public void setIsLargePower(Boolean isLargePower) {
        this.isLargePower = isLargePower;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}