package com.jopool.chargingStation.www.netty.vo.req;

import com.jopool.chargingStation.www.netty.vo.PackageData;

/**
 * Created by gexin on 2017/10/24.
 */
public class HeartbeatMsg extends PackageData {
    private String factory;//制造商名称，如：TH、DC、XY等。2位
    private String number;//车载机序列号。10位数
    private String type;//数据类型，V1心跳包；V2地址请求；V5带里程数据和电压；V6带里ICCID。2位
    private String timeHour;//时间：时/分/秒  上传时间为0时区时间，即GPS芯片数据输出时间。6位
    private String success;//数据有效位（A/V），A表示GPS数据是有效定位数据，V表示GPS数据是无效定位数据。1位
    private String latitude;//纬度，格式DDFF.FFFF, DD：纬度的度（00 ~ 90）,FF.FFFF：纬度的分（00.0000 ~ 59.9999），保留四位小数。9位
    private String latitudeFlag;//纬度标志（N：北纬，S：南纬）。1位
    private String longitude;//经度，格式DDDFF.FFFF，DDD：经度的度（000 ~ 180），FF.FFFF：经度的分（00.0000 ~ 59.9999），保留四位小数。10位
    private String longitudeFlag;//经度标志（E：东经，W：西经）。1位
    private String timeYear;//时间：日/月/年。6位
    private String vehicleStatus;//车辆状态，共四字节，表示车载机部件状态、车辆部件状态以及报警状态等。用ASCII字符表示16进制值，下面是该变量中各字节的每一位的具体含义，bit表示采用负逻辑，即bit=0有效
    private String netMcc;//移动国家码
    private String netMnc;//移动网络码
    private String netLac;//基站区域码
    private String netCellId;//基站编码
    private String mile;//里程 单位是米
    private String vol;//

    public HeartbeatMsg() {
        super();
    }

    public HeartbeatMsg(PackageData packageData) {
        setMsgHeader(packageData.getMsgHeader());
        setChannel(packageData.getChannel());
        String datas[] = new String(packageData.getMsgBodyBytes()).split(",");
        if (datas.length >= 17) {
            this.factory = datas[0];
            this.number = datas[1];
            this.type = datas[2];
            this.timeHour = datas[3];
            this.success = datas[4];
            this.latitude = datas[5];
            this.latitudeFlag = datas[6];
            this.longitude = datas[7];
            this.longitudeFlag = datas[8];
            this.timeYear = datas[9];
            this.vehicleStatus = datas[10];
            this.netMcc = datas[11];
            this.netMnc = datas[12];
            this.netLac = datas[13];
            this.netCellId = datas[14];
            this.mile = datas[15];
            this.vol = datas[16];
        }
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(String timeHour) {
        this.timeHour = timeHour;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitudeFlag() {
        return latitudeFlag;
    }

    public void setLatitudeFlag(String latitudeFlag) {
        this.latitudeFlag = latitudeFlag;
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

    public String getTimeYear() {
        return timeYear;
    }

    public void setTimeYear(String timeYear) {
        this.timeYear = timeYear;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getNetMcc() {
        return netMcc;
    }

    public void setNetMcc(String netMcc) {
        this.netMcc = netMcc;
    }

    public String getNetMnc() {
        return netMnc;
    }

    public void setNetMnc(String netMnc) {
        this.netMnc = netMnc;
    }

    public String getNetLac() {
        return netLac;
    }

    public void setNetLac(String netLac) {
        this.netLac = netLac;
    }

    public String getNetCellId() {
        return netCellId;
    }

    public void setNetCellId(String netCellId) {
        this.netCellId = netCellId;
    }

    public String getMile() {
        return mile;
    }

    public void setMile(String mile) {
        this.mile = mile;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}
