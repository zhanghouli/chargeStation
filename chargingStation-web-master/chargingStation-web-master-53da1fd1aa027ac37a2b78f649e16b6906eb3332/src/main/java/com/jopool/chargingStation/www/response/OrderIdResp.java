package com.jopool.chargingStation.www.response;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月07日 下午9:59
 */
public class OrderIdResp {
    private String orderId;
    private String rechargeOrderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRechargeOrderId() {
        return rechargeOrderId;
    }

    public void setRechargeOrderId(String rechargeOrderId) {
        this.rechargeOrderId = rechargeOrderId;
    }
}
