package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.enums.PassportStatusEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.jweb.utils.PasswordHash;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class Passport {
    private String id;

    private String realName;

    private String userName;

    private String phone;

    private String password;

    private String type;

    private String status;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public Passport() {
    }

    public Passport(String phone, String name) {
        this.id = UUIDUtils.createId();
        this.userName = phone;
        this.realName = name;
        this.password = PasswordHash.createHash(Constants.DEFAULT_PWD, UUIDUtils.createId());
        this.type = PassportTypeEnum.CAROWNER.getValue();
        this.phone = phone;
        this.status = PassportStatusEnum.NORMAL.getValue();
    }

    public Passport(String phone, WxInfo wxInfo) {
        this(phone, phone);
        if (null != wxInfo) {
            this.realName = wxInfo.getNickname();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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