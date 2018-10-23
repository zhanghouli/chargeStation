package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.Passport;

import java.util.Date;

/**
 * Created by synn on 2017/9/1.
 */
public class PassportOrCarOwnerVo {
    private String id;
    private String carOwnerId;
    private String realName;
    private String userName;
    private String phone;
    private String avatar;
    private String status;
    private String deviceNumber;
    private Date   creationTime;
    private Date   modifyTime;
    private String pic;
    private String picId;

    public  PassportOrCarOwnerVo(){

    }

    public PassportOrCarOwnerVo(Passport passport, Carowner carowner){
        this.id=passport.getId();
        this.carOwnerId=carowner!=null?carowner.getId():"";
        this.realName=passport.getRealName();
        this.userName=passport.getUserName();
        this.phone=passport.getPhone();
        this.deviceNumber=carowner!=null?carowner.getDeviceNumber():"";
        this.avatar=carowner!=null?carowner.getAvatar():"";
        this.status=passport.getStatus();
        this.creationTime=passport.getCreationTime();
        this.modifyTime=passport.getModifyTime();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}
