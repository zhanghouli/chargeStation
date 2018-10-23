package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class CommonTokenAuthAider {
    private String id;

    private String passportId;

    private Date authDatetime;

    private Date authExpiredDatetime;

    private String authSn;

    private String authPurpose;

    private String snsNo;

    private String snsType;

    private Boolean isValidToken;

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

    public Date getAuthDatetime() {
        return authDatetime;
    }

    public void setAuthDatetime(Date authDatetime) {
        this.authDatetime = authDatetime;
    }

    public Date getAuthExpiredDatetime() {
        return authExpiredDatetime;
    }

    public void setAuthExpiredDatetime(Date authExpiredDatetime) {
        this.authExpiredDatetime = authExpiredDatetime;
    }

    public String getAuthSn() {
        return authSn;
    }

    public void setAuthSn(String authSn) {
        this.authSn = authSn;
    }

    public String getAuthPurpose() {
        return authPurpose;
    }

    public void setAuthPurpose(String authPurpose) {
        this.authPurpose = authPurpose;
    }

    public String getSnsNo() {
        return snsNo;
    }

    public void setSnsNo(String snsNo) {
        this.snsNo = snsNo;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public Boolean getIsValidToken() {
        return isValidToken;
    }

    public void setIsValidToken(Boolean isValidToken) {
        this.isValidToken = isValidToken;
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