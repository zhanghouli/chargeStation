package com.jopool.chargingStation.www.netty.vo.req;

import com.jopool.chargingStation.www.netty.util.BytesUtils;
import com.jopool.chargingStation.www.netty.vo.PackageData;

/**
 * Created by gexin on 2017/10/24.
 */
public class LocationMsg extends PackageData {
    private String time;//时间。3位
    private String date;//日期。3位
    private String latitude;//纬度。4位g
    private String battery;//备用电池电量。1位
    private String longitude;//经度。4位
    private String longitudeFlag;//经度。1位
    private String spead;//速度、方向。3位
    private String vehicleStatus;//车辆状态。4位
    private String mile;//里程 单位是米。4位
    private String blank;//预留。4位
    private String mcc;//移动国家码。2位
    private String mnc;//移动网络码。1位
    private String lac;//基站区域码。2位
    private String cellId;//基站编码。2位
    private String logId;//记录号。1位

    public LocationMsg() {
    }

    public LocationMsg(PackageData packageData) {
        setMsgHeader(packageData.getMsgHeader());
        setChannel(packageData.getChannel());
        byte[] bodys = packageData.getMsgBodyBytes();
        this.time = BytesUtils.parseHexStringFromBytes(bodys, 5, 3);
        this.date = BytesUtils.parseHexStringFromBytes(bodys, 8, 3);
        this.latitude = BytesUtils.parseHexStringFromBytes(bodys, 11, 4);
        this.battery = BytesUtils.parseHexStringFromBytes(bodys, 15, 1);
        this.longitude = BytesUtils.parseHexStringFromBytes(bodys, 16, 4);
        this.longitudeFlag = BytesUtils.parseHexStringFromBytes(bodys, 20, 1);
        this.spead = BytesUtils.parseHexStringFromBytes(bodys, 21, 3);
        this.vehicleStatus = BytesUtils.parseHexStringFromBytes(bodys, 24, 4);
        this.mile = BytesUtils.parseHexStringFromBytes(bodys, 28, 4);
        this.blank = BytesUtils.parseHexStringFromBytes(bodys, 32, 4);
        this.mcc = BytesUtils.parseHexStringFromBytes(bodys, 36, 2);
        this.mnc = BytesUtils.parseHexStringFromBytes(bodys, 38, 1);
        this.lac = BytesUtils.parseHexStringFromBytes(bodys, 39, 2);
        this.cellId = BytesUtils.parseHexStringFromBytes(bodys, 41, 2);
        this.logId = BytesUtils.parseHexStringFromBytes(bodys, 43, 1);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitudeFlag() {
        return longitudeFlag;
    }

    public void setLongitudeFlag(String longitudeFlag) {
        this.longitudeFlag = longitudeFlag;
    }

    public String getSpead() {
        return spead;
    }

    public void setSpead(String spead) {
        this.spead = spead;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getMile() {
        return mile;
    }

    public void setMile(String mile) {
        this.mile = mile;
    }

    public String getBlank() {
        return blank;
    }

    public void setBlank(String blank) {
        this.blank = blank;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
