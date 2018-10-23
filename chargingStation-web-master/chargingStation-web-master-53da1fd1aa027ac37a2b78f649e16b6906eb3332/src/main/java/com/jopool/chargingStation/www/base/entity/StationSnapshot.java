package com.jopool.chargingStation.www.base.entity;

import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class StationSnapshot {
    private String id;

    private String number;

    private String name;

    private String area;

    private String areaDes;

    private Integer lngE5;

    private Integer latE5;

    private String address;

    private Integer portCount;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public StationSnapshot(String creator, Station station) {
        this.id = UUIDUtils.createId();
        this.number = station.getNumber();
        this.name = station.getName();
        this.area = station.getArea();
        this.areaDes = station.getAreaDes();
        this.lngE5 = station.getLngE5();
        this.latE5 = station.getLatE5();
        this.address = station.getAddress();
        this.creator = creator;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaDes() {
        return areaDes;
    }

    public void setAreaDes(String areaDes) {
        this.areaDes = areaDes;
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

    public Integer getPortCount() {
        return portCount;
    }

    public void setPortCount(Integer portCount) {
        this.portCount = portCount;
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