package com.jopool.chargingStation.www.vo;

import java.util.Date;

/**
 * Created by synn on 2017/11/20.
 */
public class OrderListVo {
    private String id;
    private String carownerId;
    private String carownerName;
    private String carownerPhone;
    private String stationName;
    private String stationNumber;
    private String stationAddress;
    private String stationPortId;
    private String stationPort;//充电口
    private String orderStatus;//订单状态
    private int actualTime;//实际充值时间
    private int    payment;//订单金额
    private Date   creationTime;//开单日期
    private String remark;
    private Date startTime;
    private Date endTime;

    public OrderListVo() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarownerId() {
        return carownerId;
    }

    public void setCarownerId(String carownerId) {
        this.carownerId = carownerId;
    }

    public String getCarownerName() {
        return carownerName;
    }

    public void setCarownerName(String carownerName) {
        this.carownerName = carownerName;
    }

    public String getCarownerPhone() {
        return carownerPhone;
    }

    public void setCarownerPhone(String carownerPhone) {
        this.carownerPhone = carownerPhone;
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

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationPort() {
        return stationPort;
    }

    public void setStationPort(String stationPort) {
        this.stationPort = stationPort;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStationPortId() {
        return stationPortId;
    }

    public void setStationPortId(String stationPortId) {
        this.stationPortId = stationPortId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
