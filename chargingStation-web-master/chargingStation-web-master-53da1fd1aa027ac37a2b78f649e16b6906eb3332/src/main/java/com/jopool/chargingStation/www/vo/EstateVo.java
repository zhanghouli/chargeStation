package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Estate;

/**
 * Created by synn on 2017/8/28.
 */
public class EstateVo extends Estate {

    private String  realName;

    private String operatorName;

    public EstateVo(Estate estate){
        this.setId(estate.getId());
        this.setContactName(estate.getContactName());
        this.setContactPhone(estate.getContactPhone());
        this.setElectricBill(estate.getElectricBill());
        this.setCreationTime(estate.getCreationTime());
        this.setModifyTime(estate.getModifyTime());
        this.setIsEnabled(estate.getIsEnabled());
        this.setAddress(estate.getAddress());
        this.setOperatorId(estate.getOperatorId());
    }


    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
