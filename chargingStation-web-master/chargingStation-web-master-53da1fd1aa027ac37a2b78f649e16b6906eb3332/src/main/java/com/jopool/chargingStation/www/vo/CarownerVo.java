package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.base.entity.Passport;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.vo
 * @Author : soupcat
 * @Creation Date : 2017年09月01日 下午3:29
 */
public class CarownerVo {
    private Passport passport;
    private Carowner carowner;

    public CarownerVo(Passport passport, Carowner carowner) {
        this.passport = passport;
        this.carowner = carowner;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Carowner getCarowner() {
        return carowner;
    }

    public void setCarowner(Carowner carowner) {
        this.carowner = carowner;
    }
}
