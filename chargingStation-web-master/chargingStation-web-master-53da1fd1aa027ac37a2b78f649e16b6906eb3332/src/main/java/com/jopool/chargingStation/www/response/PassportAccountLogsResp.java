package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.PassportAccountLog;

import java.util.Date;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年08月28日 下午10:56
 */
public class PassportAccountLogsResp {
    private String id;
    private int    amount;
    private int    amountBefore;
    private int    amountAfter;
    private String type;
    private String remark;
    private Date   creationTime;

    public PassportAccountLogsResp(PassportAccountLog log) {
        this.id = log.getId();
        this.amount = log.getAmount();
        this.amountBefore = log.getAmountBefore();
        this.amountAfter = log.getAmountAfter();
        this.type = log.getType();
        this.remark = log.getRemark();
        this.creationTime = log.getCreationTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(int amountBefore) {
        this.amountBefore = amountBefore;
    }

    public int getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(int amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
