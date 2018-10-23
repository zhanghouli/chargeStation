package com.jopool.chargingStation.www.vo;

/**
 * Created by synn on 2017/9/8.
 */
public class StationPortInfoVo {
    private String id;
    private String numberStation;//电站编码
    private String seq;//序号
    private String numberPort;//电口编码
    private String stationName;//电站名字
    private String stationAddress;//电站地址
    private String operatorRealName;//运营商名字
    private String estateName;//物业名字
    private String estatePhone;//物业手机号

    public StationPortInfoVo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberStation() {
        return numberStation;
    }

    public void setNumberStation(String numberStation) {
        this.numberStation = numberStation;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getNumberPort() {
        return numberPort;
    }

    public void setNumberPort(String numberPort) {
        this.numberPort = numberPort;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getOperatorRealName() {
        return operatorRealName;
    }

    public void setOperatorRealName(String operatorRealName) {
        this.operatorRealName = operatorRealName;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public String getEstatePhone() {
        return estatePhone;
    }

    public void setEstatePhone(String estatePhone) {
        this.estatePhone = estatePhone;
    }
}
