package com.jopool.chargingStation.www.request;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.request
 * @Author : soupcat
 * @Creation Date : 2017年09月01日 下午1:55
 */
public class StationReq {
    private int lngE5 = 0;
    private int latE5 = 0;
    private String keyword;
    private String city;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
