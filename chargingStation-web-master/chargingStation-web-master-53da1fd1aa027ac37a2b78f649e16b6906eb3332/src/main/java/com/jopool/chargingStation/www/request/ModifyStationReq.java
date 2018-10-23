package com.jopool.chargingStation.www.request;


/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.request
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 下午6:21
 */
public class ModifyStationReq {
    private String  stationId;
    private int     electricBill;
    private int     operatorSharingRatio;
    private int     estateSharingRatio;
    private String  consumePackageId;
    private String  address;
    private Integer lngE5;
    private Integer latE5;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(int electricBill) {
        this.electricBill = electricBill;
    }

    public int getOperatorSharingRatio() {
        return operatorSharingRatio;
    }

    public void setOperatorSharingRatio(int operatorSharingRatio) {
        this.operatorSharingRatio = operatorSharingRatio;
    }

    public int getEstateSharingRatio() {
        return estateSharingRatio;
    }

    public void setEstateSharingRatio(int estateSharingRatio) {
        this.estateSharingRatio = estateSharingRatio;
    }

    public String getConsumePackageId() {
        return consumePackageId;
    }

    public void setConsumePackageId(String consumePackageId) {
        this.consumePackageId = consumePackageId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLngE5() {
        return lngE5;
    }

    public void setLngE5(Integer lngE5) {
        this.lngE5 = lngE5;
    }

    public Integer getLatE5() {
        return latE5;
    }

    public void setLatE5(Integer latE5) {
        this.latE5 = latE5;
    }
}
