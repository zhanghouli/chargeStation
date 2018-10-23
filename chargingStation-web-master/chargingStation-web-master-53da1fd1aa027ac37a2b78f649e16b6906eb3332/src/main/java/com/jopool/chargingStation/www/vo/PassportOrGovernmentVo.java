package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Government;
import com.jopool.chargingStation.www.base.entity.Operator;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.jweb.utils.StringUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by synn on 2017/10/24.
 */
public class PassportOrGovernmentVo {
    private   String id;
    private   String governmentId;
    private   String area;
    private   String realName;//真实姓名
    private   String userName;//登录名
    private   String pic;//图片
    private   String picId;//图片ID
    private   String phone;//手机号
    private   String status;//状态
    private   String type;
    private   String areaDes;//政府地址
    private   Date   creationTime;
    private   Date   modifyTime;
    public PassportOrGovernmentVo(){

    }
    public PassportOrGovernmentVo(Passport passport, Government government) {
        this.id = passport.getId();
        this.governmentId = government.getId();
        this.realName = passport.getRealName();
        this.userName = passport.getUserName();
        this.phone = passport.getPhone();
        this.area = government.getArea();
        this.status = passport.getStatus();
        this.type = passport.getType();
        this.areaDes = getCitys(government);
        this.creationTime = passport.getCreationTime();
        this.modifyTime = passport.getModifyTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaDes() {
        return areaDes;
    }

    public void setAreaDes(String areaDes) {
        this.areaDes = areaDes;
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

    private String getCitys(Government government) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(government.getAreaDes())) {
            String[] citys = government.getAreaDes().split(",");
            for (String name : Arrays.asList(citys)) {
                sb.append(name + "-");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
