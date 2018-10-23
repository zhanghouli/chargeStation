package com.jopool.chargingStation.www.vo.common;

/**
 * Created by synn on 2017/8/28.
 */
public class SearchOperatorVo extends SearchBaseVo {
    private String  status;

    private String province;

    private String city;

    private String town;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
