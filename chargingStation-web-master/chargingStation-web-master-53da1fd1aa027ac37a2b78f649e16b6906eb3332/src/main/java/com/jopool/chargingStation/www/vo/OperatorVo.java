package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Operator;

/**
 * Created by synn on 2017/8/27.
 */
public class OperatorVo extends Operator {
    private String province;

    private String city;

    private String town;

    private String realName;

    public OperatorVo(Operator operator){
        this.setId(operator.getId());
        this.setPassportId(operator.getPassportId());
        this.setArea(operator.getArea());
        this.setAreaDes(operator.getAreaDes());
        this.setCreationTime(operator.getCreationTime());
        this.setIsEnabled(operator.getIsEnabled());
        this.setModifyTime(operator.getModifyTime());
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
