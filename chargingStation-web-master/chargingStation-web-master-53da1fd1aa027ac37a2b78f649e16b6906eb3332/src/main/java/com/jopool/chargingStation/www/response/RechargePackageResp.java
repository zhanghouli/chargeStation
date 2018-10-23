package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.RechargePackage;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 下午4:52
 */
public class RechargePackageResp {
    private String id;
    private int    payment;
    private int    amount;

    public RechargePackageResp(RechargePackage rechargePackage) {
        this.id = rechargePackage.getId();
        this.payment = rechargePackage.getPayment();
        this.amount = rechargePackage.getAmount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
