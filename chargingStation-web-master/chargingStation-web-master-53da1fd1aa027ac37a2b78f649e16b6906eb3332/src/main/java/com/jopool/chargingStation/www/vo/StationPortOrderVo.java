package com.jopool.chargingStation.www.vo;

import java.util.Date;

/**
 * Created by synn on 2017/9/7.
 * 电口充电记录
 */

public class StationPortOrderVo {
    private String id;//order Id
    private String stationName;//电站名字1
    private String portName;//电口编号1
    private String carOwnerName;//车主姓名1
    private String operatorName;//运营商姓名1
    private String payment;//支付金额
    private Date   creationTime;//充值时间

    public StationPortOrderVo() {

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

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
