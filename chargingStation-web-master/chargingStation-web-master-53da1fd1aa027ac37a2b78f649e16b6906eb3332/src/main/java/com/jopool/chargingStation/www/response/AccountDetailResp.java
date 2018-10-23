package com.jopool.chargingStation.www.response;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 上午10:19
 */
public class AccountDetailResp {
    private String id;
    private int    amount;
    private Integer    incomeToday;
    private Integer    incomeThisMonth;
    private Integer    incomeLastMonth;

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

    public Integer getIncomeToday() {
        return incomeToday;
    }

    public void setIncomeToday(Integer incomeToday) {
        this.incomeToday = incomeToday;
    }

    public Integer getIncomeThisMonth() {
        return incomeThisMonth;
    }

    public void setIncomeThisMonth(Integer incomeThisMonth) {
        this.incomeThisMonth = incomeThisMonth;
    }

    public Integer getIncomeLastMonth() {
        return incomeLastMonth;
    }

    public void setIncomeLastMonth(Integer incomeLastMonth) {
        this.incomeLastMonth = incomeLastMonth;
    }
}
