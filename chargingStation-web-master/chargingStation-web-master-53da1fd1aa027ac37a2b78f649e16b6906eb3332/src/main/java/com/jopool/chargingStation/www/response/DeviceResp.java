package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Carowner;

/**
 * Created by gexin on 2017/10/27.
 */
public class DeviceResp {
    private String id;
    private int    lnge5;
    private int    late5;
    private String deviceNumber;
    private String deviceStatus;
    private String fenceStatus;

    public DeviceResp(Carowner carowner) {
        this.id = carowner.getId();
        this.lnge5 = null == carowner.getBdLng() ? 0 : carowner.getBdLng();
        this.late5 = null == carowner.getBdLat() ? 0 : carowner.getBdLat();
        this.deviceNumber = carowner.getDeviceNumber();
        this.deviceStatus = carowner.getDeviceStatus();
        this.fenceStatus = carowner.getFenceStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLnge5() {
        return lnge5;
    }

    public void setLnge5(int lnge5) {
        this.lnge5 = lnge5;
    }

    public int getLate5() {
        return late5;
    }

    public void setLate5(int late5) {
        this.late5 = late5;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getFenceStatus() {
        return fenceStatus;
    }

    public void setFenceStatus(String fenceStatus) {
        this.fenceStatus = fenceStatus;
    }
}
