package com.jopool.chargingStation.www.base.entity;

import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class ChargeCostPackage {
    private String id;

    private Integer minPow;

    private Integer maxPow;

    private Integer cost;

    private String remark;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public ChargeCostPackage() {
    }

    public ChargeCostPackage(ChargeCostPackage chargeCostPackage,String creatorId) {
        this.id = UUIDUtils.createId();
        this.minPow = chargeCostPackage.getMinPow();
        this.maxPow = chargeCostPackage.getMaxPow();
        this.cost = chargeCostPackage.getCost();
        this.remark = chargeCostPackage.getRemark();
        this.creator = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMinPow() {
        return minPow;
    }

    public void setMinPow(Integer minPow) {
        this.minPow = minPow;
    }

    public Integer getMaxPow() {
        return maxPow;
    }

    public void setMaxPow(Integer maxPow) {
        this.maxPow = maxPow;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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