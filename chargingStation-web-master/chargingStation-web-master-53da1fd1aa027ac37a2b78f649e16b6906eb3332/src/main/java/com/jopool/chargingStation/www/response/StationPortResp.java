package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.StationPort;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月01日 下午3:21
 */
public class StationPortResp {
    private String  id;
    private String  stationId;
    private int     seq;
    private String  number;
    private String  qrCode;
    private String  status;
    private int     restTime;//分钟
    private String  power;//功率
    private int     maxPower;
    private int     minPower;
    private int     trickleTime;//涓流时间
    private boolean isAutoStop;
    private boolean isLargePower;
    private String  orderId;


    public StationPortResp(StationPort stationPort) {
        this.id = stationPort.getId();
        this.stationId = stationPort.getStationId();
        this.seq = stationPort.getSeq();
        this.number = stationPort.getNumber();
        this.qrCode = stationPort.getQrCode();
        this.status = stationPort.getStatus();
        this.maxPower = stationPort.getMaxPower();
        this.minPower = stationPort.getMinPower();
        this.trickleTime = stationPort.getTrickleTime();
        this.isAutoStop = stationPort.getIsAutoStop();
        this.isLargePower = stationPort.getIsLargePower();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getMinPower() {
        return minPower;
    }

    public void setMinPower(int minPower) {
        this.minPower = minPower;
    }

    public int getTrickleTime() {
        return trickleTime;
    }

    public void setTrickleTime(int trickleTime) {
        this.trickleTime = trickleTime;
    }

    public boolean isAutoStop() {
        return isAutoStop;
    }

    public void setAutoStop(boolean autoStop) {
        isAutoStop = autoStop;
    }

    public boolean isLargePower() {
        return isLargePower;
    }

    public void setLargePower(boolean largePower) {
        isLargePower = largePower;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
