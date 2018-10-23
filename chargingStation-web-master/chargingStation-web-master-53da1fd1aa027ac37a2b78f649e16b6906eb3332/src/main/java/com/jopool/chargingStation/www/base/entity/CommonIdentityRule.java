package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class CommonIdentityRule {
    private String id;

    private String ruleCode;

    private String ruleName;

    private Integer currentValue;

    private Short ruleLength;

    private String rulePrefix;

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

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Short getRuleLength() {
        return ruleLength;
    }

    public void setRuleLength(Short ruleLength) {
        this.ruleLength = ruleLength;
    }

    public String getRulePrefix() {
        return rulePrefix;
    }

    public void setRulePrefix(String rulePrefix) {
        this.rulePrefix = rulePrefix;
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