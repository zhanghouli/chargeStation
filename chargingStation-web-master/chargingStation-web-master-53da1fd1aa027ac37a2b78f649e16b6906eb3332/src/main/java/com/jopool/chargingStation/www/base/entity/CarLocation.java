package com.jopool.chargingStation.www.base.entity;

import com.jopool.chargingStation.www.base.utils.GPSUtil;
import com.jopool.chargingStation.www.netty.vo.req.LocationMsg;
import com.jopool.jweb.utils.MathUtils;
import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class CarLocation {
    private String id;

    private String carownerId;

    private String deviceNumber;

    private String time;

    private String date;

    private String latitude;

    private int bdLat;

    private String longitude;

    private int bdLng;

    private String longitudeFlag;

    private String battery;

    private String spead;

    private String vehicleStatus;

    private String mile;

    private String blank;

    private String mcc;

    private String mnc;

    private String lac;

    private String cellId;

    private String logId;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public CarLocation() {
    }

    public CarLocation(LocationMsg msg) {
        this.id = UUIDUtils.createId();
        this.deviceNumber = msg.getMsgHeader().getDeviceNumber();
        this.time = msg.getTime();
        this.date = msg.getDate();
        this.latitude = msg.getLatitude();
        this.longitude = msg.getLongitude();
        this.longitudeFlag = msg.getLongitudeFlag();
        this.battery = msg.getBattery();
        this.spead = msg.getSpead();
        this.vehicleStatus = msg.getVehicleStatus();
        this.mile = msg.getMile();
        this.blank = msg.getBlank();
        this.mcc = msg.getMcc();
        this.mnc = msg.getMnc();
        this.lac = msg.getLac();
        this.cellId = msg.getCellId();
        this.logId = msg.getLogId();
        this.isDeleted = false;
        //
        GPSUtil.Gps gps = GPSUtil.toBaiduGPS(MathUtils.div(Double.parseDouble(this.longitude), 1E5, 5), MathUtils.div(Double.parseDouble(this.latitude), 1E6, 6));
        if (null != gps) {
            this.bdLng = (int) (gps.getLng() * 1E5);
            this.bdLat = (int) (gps.getLat() * 1E5);
        }
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

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
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

    public int getBdLat() {
        return bdLat;
    }

    public void setBdLat(int bdLat) {
        this.bdLat = bdLat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getBdLng() {
        return bdLng;
    }

    public void setBdLng(int bdLng) {
        this.bdLng = bdLng;
    }

    public String getLongitudeFlag() {
        return longitudeFlag;
    }

    public void setLongitudeFlag(String longitudeFlag) {
        this.longitudeFlag = longitudeFlag;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}