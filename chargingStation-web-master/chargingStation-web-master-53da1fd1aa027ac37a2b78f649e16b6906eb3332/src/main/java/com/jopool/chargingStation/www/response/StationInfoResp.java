package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.CarownerStationKey;

import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月04日 下午2:52
 */
public class StationInfoResp {
    private OperatorStationResp          station;
    private List<StationPortResp>        ports;
    private List<ConsumePackageItemResp> consumePackages;
    private boolean                      collect;
    private String                       contactPhone;

    public StationInfoResp(OperatorStationResp station, List<StationPortResp> ports, List<ConsumePackageItemResp> consumePackages, CarownerStationKey carownerStationKey) {
        this.station = station;
        this.ports = ports;
        this.consumePackages = consumePackages;
        this.collect = carownerStationKey == null ? false : true;
    }

    public OperatorStationResp getStation() {
        return station;
    }

    public void setStation(OperatorStationResp station) {
        this.station = station;
    }

    public List<StationPortResp> getPorts() {
        return ports;
    }

    public void setPorts(List<StationPortResp> ports) {
        this.ports = ports;
    }

    public List<ConsumePackageItemResp> getConsumePackages() {
        return consumePackages;
    }

    public void setConsumePackages(List<ConsumePackageItemResp> consumePackages) {
        this.consumePackages = consumePackages;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
