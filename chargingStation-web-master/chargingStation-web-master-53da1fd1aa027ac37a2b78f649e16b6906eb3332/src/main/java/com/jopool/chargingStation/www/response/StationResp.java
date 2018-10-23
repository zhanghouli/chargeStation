package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Station;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月01日 下午1:20
 */
public class StationResp {
    private String  id;
    private String  number;
    private String  name;
    private String  area;
    private String  areDes;
    private int     lngE5;
    private int     latE5;
    private String  address;
    //距离
    private String  distance;
    private int     portCount;
    private int     workCount;
    private int     freeCount;
    private String  status;
    private Integer amount;

    public StationResp(Station station) {
        this.id = station.getId();
        this.number = station.getNumber();
        this.name = station.getName();
        this.area = station.getArea();
        this.areDes = station.getAreaDes();
        this.lngE5 = station.getLngE5();
        this.latE5 = station.getLatE5();
        this.address = station.getAddress();
        this.portCount = station.getPortCount();
        this.status = station.getStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreDes() {
        return areDes;
    }

    public void setAreDes(String areDes) {
        this.areDes = areDes;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getPortCount() {
        return portCount;
    }

    public void setPortCount(int portCount) {
        this.portCount = portCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }
}
