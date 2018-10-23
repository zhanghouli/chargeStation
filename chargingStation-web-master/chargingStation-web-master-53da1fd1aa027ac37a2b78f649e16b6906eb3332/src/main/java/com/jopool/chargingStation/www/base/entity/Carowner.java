package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.enums.DeviceStatusEnum;
import com.jopool.chargingStation.www.enums.FenceStatusEnum;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class Carowner {
    private String id;

    private String passportId;

    private String openId;

    private String deviceNumber;

    private String deviceStatus;

    private String fenceStatus;

    private Integer lat;

    private Integer lng;

    private Integer bdLat;

    private Integer bdLng;

    private String avatar;

    private Integer historyRestTime;

    private String clientId;

    private String useStatus;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public Carowner() {

    }

    public Carowner(Passport passport, WxInfo wxInfo) {
        this.id = UUIDUtils.createId();
        this.passportId = passport.getId();
        this.deviceStatus = DeviceStatusEnum.OFFLINE.getValue();
        this.fenceStatus = FenceStatusEnum.UNKNOW.getValue();
        if (null != wxInfo) {
            this.openId = wxInfo.getOpenId();
            this.avatar = wxInfo.getHeadimgurl();
        }
    }

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getFenceStatus() {
        return fenceStatus;
    }

    public void setFenceStatus(String fenceStatus) {
        this.fenceStatus = fenceStatus;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public Integer getBdLat() {
        return bdLat;
    }

    public void setBdLat(Integer bdLat) {
        this.bdLat = bdLat;
    }

    public Integer getBdLng() {
        return bdLng;
    }

    public void setBdLng(Integer bdLng) {
        this.bdLng = bdLng;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getHistoryRestTime() {
        return historyRestTime;
    }

    public void setHistoryRestTime(Integer historyRestTime) {
        this.historyRestTime = historyRestTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
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