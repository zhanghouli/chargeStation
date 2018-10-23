package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Operator;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.jweb.utils.StringUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by synn on 2017/9/1.
 */
public class PassportOrOperatorVo {
    private String id;
    private String operatorId;
    private String area;
    private String realName;//真实姓名
    private String userName;//登录名
    private String pic;//图片
    private String picId;//图片ID
    private String phone;//手机号
    private String status;//状态
    private String type;
    private String areaDes;//运营商地址
    private Date   creationTime;
    private Date   modifyTime;

    public PassportOrOperatorVo() {

    }

    public PassportOrOperatorVo(Passport passport, Operator operator) {
        this.id = passport.getId();
        this.operatorId = operator.getId();
        this.realName = passport.getRealName();
        this.userName = passport.getUserName();
        this.phone = passport.getPhone();
        this.area = operator.getArea();
        this.status = passport.getStatus();
        this.type = passport.getType();
        this.areaDes = getCitys(operator);
        this.creationTime = passport.getCreationTime();
        this.modifyTime = passport.getModifyTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(this.areaDes)) {
            String[] citys = this.areaDes.split(",");
            for (String name : Arrays.asList(citys)) {
                sb.append(name + "-");
            }
        }

        return sb.toString().substring(0, sb.toString().length() - 1);
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

    private String getCitys(Operator operator) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(operator.getAreaDes())) {
            String[] citys = operator.getAreaDes().split(",");
            for (String name : Arrays.asList(citys)) {
                sb.append(name + "-");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
