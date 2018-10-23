package com.jopool.chargingStation.www.base.entity;

public class CarownerStationKey {
    private String carownerId;

    private String stationId;

    public String getCarownerId() {
        return carownerId;
    }

    public void setCarownerId(String carownerId) {
        this.carownerId = carownerId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}