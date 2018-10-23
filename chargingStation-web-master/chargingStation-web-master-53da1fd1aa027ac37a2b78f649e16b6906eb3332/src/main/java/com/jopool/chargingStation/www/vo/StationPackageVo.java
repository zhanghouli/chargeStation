package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Station;

import java.util.Map;

/**
 * Created by synn on 2017/9/5.
 */
public class StationPackageVo {

    private Map<String, String> maps;

    private Station station;

    public StationPackageVo() {

    }

    public StationPackageVo(Station station) {
            this.station=station;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
