package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class StationFault {
    private String id;

    private String passportId;

    private String stationId;

    private String stationName;

    private Integer lngE5;

    private Integer latE5;

    private String address;

    private String remark;

    private String pics;

    private Boolean isViewed;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    private String bizDealResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getLngE5() {
        return lngE5;
    }

    public void setLngE5(Integer lngE5) {
        this.lngE5 = lngE5;
    }

    public Integer getLatE5() {
        return latE5;
    }

    public void setLatE5(Integer latE5) {
        this.latE5 = latE5;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public Boolean getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Boolean isViewed) {
        this.isViewed = isViewed;
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

    public String getBizDealResult() {
        return bizDealResult;
    }

    public void setBizDealResult(String bizDealResult) {
        this.bizDealResult = bizDealResult;
    }
}