package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;

import java.util.Date;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月04日 下午2:59
 */
public class ConsumePackageItemResp {
    private String id;
    private String startTime;
    private String endTime;
    private int    payment;
    private int    time;

    public ConsumePackageItemResp(ConsumePackageItem item) {
        this.id = item.getId();
        this.startTime = item.getStartTime();
        this.endTime = item.getEndTime();
        this.payment = item.getPayment();
        this.time = item.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
