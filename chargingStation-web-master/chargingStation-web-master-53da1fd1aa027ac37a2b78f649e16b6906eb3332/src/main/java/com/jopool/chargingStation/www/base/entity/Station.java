package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class Station {
    private String id;

    private String operatorId;

    private String estateId;

    private String number;

    private String name;

    private String area;

    private String areaDes;

    private Integer lngE5;

    private Integer latE5;

    private String address;

    private Integer portCount;

    private String status;

    private String consumePackageId;

    private Integer electricBill;

    private Integer operatorSharingRatio;

    private Integer estateSharingRatio;

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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsumePackageId() {
        return consumePackageId;
    }

    public void setConsumePackageId(String consumePackageId) {
        this.consumePackageId = consumePackageId;
    }

    public Integer getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(Integer electricBill) {
        this.electricBill = electricBill;
    }

    public Integer getOperatorSharingRatio() {
        return operatorSharingRatio;
    }

    public void setOperatorSharingRatio(Integer operatorSharingRatio) {
        this.operatorSharingRatio = operatorSharingRatio;
    }

    public Integer getEstateSharingRatio() {
        return estateSharingRatio;
    }

    public void setEstateSharingRatio(Integer estateSharingRatio) {
        this.estateSharingRatio = estateSharingRatio;
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