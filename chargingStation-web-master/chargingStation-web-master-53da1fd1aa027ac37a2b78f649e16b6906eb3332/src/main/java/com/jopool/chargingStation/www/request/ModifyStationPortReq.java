package com.jopool.chargingStation.www.request;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.request
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 下午6:25
 */
public class ModifyStationPortReq {
    private String  stationPortIds;
    private String  qrCode;
    private int     maxPower;
    private int     minPower;
    private int     trickleTime;
    private Boolean autoStop;
    private Boolean largePower;

    public String getStationPortIds() {
        return stationPortIds;
    }

    public void setStationPortIds(String stationPortIds) {
        this.stationPortIds = stationPortIds;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public Boolean getAutoStop() {
        return autoStop;
    }

    public void setAutoStop(Boolean autoStop) {
        this.autoStop = autoStop;
    }

    public Boolean getLargePower() {
        return largePower;
    }

    public void setLargePower(Boolean largePower) {
        this.largePower = largePower;
    }
}
