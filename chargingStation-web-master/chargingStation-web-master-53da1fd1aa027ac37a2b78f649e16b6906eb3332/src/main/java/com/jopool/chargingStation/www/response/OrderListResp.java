package com.jopool.chargingStation.www.response;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月05日 下午4:33
 */
public class OrderListResp {
    private StationResp     station;
    private StationPortResp port;
    private OrderResp       order;
    private String          carownerName;
    private String          carownerPhone;

    public StationResp getStation() {
        return station;
    }

    public void setStation(StationResp station) {
        this.station = station;
    }

    public StationPortResp getPort() {
        return port;
    }

    public void setPort(StationPortResp port) {
        this.port = port;
    }

    public OrderResp getOrder() {
        return order;
    }

    public void setOrder(OrderResp order) {
        this.order = order;
    }

    public String getCarownerName() {
        return carownerName;
    }

    public void setCarownerName(String carownerName) {
        this.carownerName = carownerName;
    }

    public String getCarownerPhone() {
        return carownerPhone;
    }

    public void setCarownerPhone(String carownerPhone) {
        this.carownerPhone = carownerPhone;
    }
}
