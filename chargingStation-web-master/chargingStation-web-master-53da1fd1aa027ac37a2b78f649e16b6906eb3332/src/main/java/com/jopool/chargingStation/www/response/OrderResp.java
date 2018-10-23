package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Order;

import java.util.Date;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月05日 下午4:34
 */
public class OrderResp {
    private String  id;
    private String  status;
    private String  payType;
    private Integer actualPayment;
    private int     payment;
    private int     payTime;
    private int     actualTime;
    private int     restTime;//剩余时间
    private String  power;
    private String  remark;
    private Date    startTime;
    private Date    endTime;
    private Date    creationTime;
    //
    private String  rechargeOrderId;
    private String  chargeType;
    private int     cost;
    //
    private String  cyclingDistance;

    public OrderResp(Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.payType = order.getPaytype();
        if (order.getActualPayment() != null) {
            this.actualPayment = order.getActualPayment();
        }
        if (order.getPayment() != null) {
            this.payment = order.getPayment();
        }
        if (order.getPayTime() != null) {
            this.payTime = order.getPayTime();
        }
        this.actualTime = order.getActualTime();
        this.power = order.getPower();
        this.remark = order.getRemark();
        this.chargeType = order.getChargeType();
        this.startTime = order.getStartTime();
        this.endTime = order.getEndTime() == null ? null : order.getEndTime();
        this.creationTime = order.getCreationTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(Integer actualPayment) {
        this.actualPayment = actualPayment;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getPayTime() {
        return payTime;
    }

    public void setPayTime(int payTime) {
        this.payTime = payTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getRechargeOrderId() {
        return rechargeOrderId;
    }

    public void setRechargeOrderId(String rechargeOrderId) {
        this.rechargeOrderId = rechargeOrderId;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCyclingDistance() {
        return cyclingDistance;
    }

    public void setCyclingDistance(String cyclingDistance) {
        this.cyclingDistance = cyclingDistance;
    }
}
