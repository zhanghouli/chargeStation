package com.jopool.chargingStation.www.base.entity;

public class PassportAccount {
    private String passportId;

    private Integer amount;

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}