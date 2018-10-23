package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.base.entity.Passport;

import java.util.Date;

/**
 * Created by synn on 2017/9/1.
 */
public class PassportOrEstateVo {
    private String id;//passportId
    private String estateId;//物业id
    private String operatorId;//运营商Id
    private String operatorName;//运营商Name
    private String realName;//真实名字
    private String userName;//登录名字
    private String phone;//手机
    private String status;//状态
    private String pic;
    private String picId;
    private String address;//物业地址
    private String contactName;//物业姓名
    private String contactPhone;//物业手机号
    private int    electricBill;
    private Date   creationTime;
    private Date   modifyTime;

    private PassportOrEstateVo() {

    }

    public PassportOrEstateVo(Passport passport, Estate estate) {
        this.id = passport.getId();
        this.estateId = estate.getId();
        this.operatorId = estate.getOperatorId();
        this.realName = passport.getRealName();
        this.userName = passport.getUserName();
        this.phone = passport.getPhone();
        this.status = passport.getStatus();
        this.address = estate.getAddress();
        this.contactName = estate.getContactName();
        this.contactPhone = estate.getContactPhone();
        this.electricBill = estate.getElectricBill();
        this.creationTime = passport.getCreationTime();
        this.modifyTime = passport.getModifyTime();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public int getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(int electricBill) {
        this.electricBill = electricBill;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
