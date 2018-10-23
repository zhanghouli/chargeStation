package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Station;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月04日 下午2:54
 */
public class OperatorStationResp extends StationResp {
    private int    electricBill;//电费
    private int    operatorSharingRatio;//运营商分成比例
    private int    estateSharingRatio;//物业分成比例
    private String consumePackageId;

    public OperatorStationResp(Station station) {
        super(station);
        this.electricBill = station.getElectricBill();
        this.operatorSharingRatio = station.getOperatorSharingRatio();
        this.estateSharingRatio = station.getEstateSharingRatio();
        this.consumePackageId = station.getConsumePackageId();
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
}
