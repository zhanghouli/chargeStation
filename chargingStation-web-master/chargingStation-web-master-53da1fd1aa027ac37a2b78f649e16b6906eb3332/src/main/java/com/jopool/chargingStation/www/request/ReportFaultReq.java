package com.jopool.chargingStation.www.request;

import com.jopool.chargingStation.www.base.entity.StationFault;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.request
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 下午6:20
 */
public class ReportFaultReq {
    private String stationId;
    private String stationName;
    private int    lngE5;
    private int    latE5;
    private String address;
    private String remark;
    private String pics;

    public StationFault parseStationFault(String passportId) {
        StationFault fault = new StationFault();
        String id = UUIDUtils.createId();
        fault.setId(id);
        fault.setPassportId(passportId);
        fault.setStationId(stationId);
        fault.setStationName(stationName);
        fault.setLngE5(lngE5);
        fault.setLatE5(latE5);
        fault.setAddress(address);
        fault.setRemark(remark);
        fault.setPics(pics);
        fault.setCreator(passportId);
        return fault;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getLngE5() {
        return lngE5;
    }

    public void setLngE5(int lngE5) {
        this.lngE5 = lngE5;
    }

    public int getLatE5() {
        return latE5;
    }

    public void setLatE5(int latE5) {
        this.latE5 = latE5;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }
}
