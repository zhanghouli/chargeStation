package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class Order {
    private String id;

    private String carownerId;

    private String stationId;

    private String stationPortId;

    private String stationSnapshotId;

    private String stationPortSnapshotId;

    private String estateId;

    private String operatorId;

    private String status;

    private String chargeType;

    private String paytype;

    private Integer payment;

    private Integer actualPayment;

    private Integer payTime;

    private Integer actualTime;

    private String power;

    private Date startTime;

    private Date endTime;

    private String remark;

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

    public String getCarownerId() {
        return carownerId;
    }

    public void setCarownerId(String carownerId) {
        this.carownerId = carownerId;
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

    public String getStationSnapshotId() {
        return stationSnapshotId;
    }

    public void setStationSnapshotId(String stationSnapshotId) {
        this.stationSnapshotId = stationSnapshotId;
    }

    public String getStationPortSnapshotId() {
        return stationPortSnapshotId;
    }

    public void setStationPortSnapshotId(String stationPortSnapshotId) {
        this.stationPortSnapshotId = stationPortSnapshotId;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(Integer actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    public Integer getActualTime() {
        return actualTime;
    }

    public void setActualTime(Integer actualTime) {
        this.actualTime = actualTime;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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