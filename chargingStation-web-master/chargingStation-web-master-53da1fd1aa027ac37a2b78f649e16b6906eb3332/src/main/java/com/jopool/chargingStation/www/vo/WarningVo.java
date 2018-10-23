package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Station;
import com.jopool.chargingStation.www.base.entity.Warning;

import java.util.Date;

/**
 * @Project : chargingStation-web
 * @Package Name : com.jopool.chargingStation.www.vo
 * @Author : soupcat
 * @Creation Date : 2017年11月06日 下午4:30
 */
public class WarningVo {
    private String id;
    private String stationName;
    private String stationNumber;
    private String address;
    private String operatorName;
    private String estateName;
    private int    warningType;
    private String remark;
    private String temp;
    private Date   creationTime;


    public  WarningVo(){

    }

    public WarningVo(Warning warning, Station station, String operatorName, String estateName) {
        this.id = warning.getId();
        this.stationName = station.getName();
        this.stationNumber = station.getNumber();
        this.address = station.getAddress();
        this.operatorName = operatorName;
        this.estateName = estateName;
        this.warningType = warning.getType();
        this.remark = warning.getRemark();
        this.temp = warning.getTemperature();
        this.creationTime = warning.getDataTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public int getWarningType() {
        return warningType;
    }

    public void setWarningType(int warningType) {
        this.warningType = warningType;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
