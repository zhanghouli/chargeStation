package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class PassportAccountLog {
    private String id;

    private String passportId;

    private Integer amount;

    private Integer amountBefore;

    private Integer amountAfter;

    private String type;

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

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(Integer amountBefore) {
        this.amountBefore = amountBefore;
    }

    public Integer getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(Integer amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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