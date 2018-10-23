package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.base.entity.Passport;

/**
 * Created by synn on 2018/5/10.
 */
public class EstateResp {
    private String operatorId;
    private String realName;
    private String estateId;
    private String passportId;

    public EstateResp() {

    }

    public EstateResp(Passport passport, Estate estate) {
        this.operatorId = estate.getOperatorId();
        this.estateId = estate.getId();
        this.passportId = passport.getId();
        this.realName = passport.getRealName();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }
}
