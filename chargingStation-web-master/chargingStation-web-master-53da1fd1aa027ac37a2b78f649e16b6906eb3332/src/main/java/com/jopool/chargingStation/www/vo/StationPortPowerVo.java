package com.jopool.chargingStation.www.vo;

import java.util.Date;

/**
 * Created by synn on 2017/9/6.
 */
public class StationPortPowerVo {
    private Date   date;
    private String power;

    public StationPortPowerVo() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
