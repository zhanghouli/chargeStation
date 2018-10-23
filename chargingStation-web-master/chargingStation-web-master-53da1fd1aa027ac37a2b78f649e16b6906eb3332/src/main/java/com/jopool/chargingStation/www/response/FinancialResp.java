package com.jopool.chargingStation.www.response;

/**
 * @Project : chargingStation-web
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年11月10日 下午4:38
 */
public class FinancialResp {
    private int electricityFees;//电费
    private int platformFees;//平台管理费
    private int investorFees;//投资方
    private int operatorFees;//运行商管理费
    private int estateFees;//物业

    public FinancialResp() {
    }

    public FinancialResp(int electricityFees, int platformFees, int investorFees, int operatorFees, int estateFees) {
        this.electricityFees = electricityFees;
        this.platformFees = platformFees;
        this.investorFees = investorFees;
        this.operatorFees = operatorFees;
        this.estateFees = estateFees;
    }

    public int getElectricityFees() {
        return electricityFees;
    }

    public void setElectricityFees(int electricityFees) {
        this.electricityFees = electricityFees;
    }

    public int getPlatformFees() {
        return platformFees;
    }

    public void setPlatformFees(int platformFees) {
        this.platformFees = platformFees;
    }

    public int getInvestorFees() {
        return investorFees;
    }

    public void setInvestorFees(int investorFees) {
        this.investorFees = investorFees;
    }

    public int getOperatorFees() {
        return operatorFees;
    }

    public void setOperatorFees(int operatorFees) {
        this.operatorFees = operatorFees;
    }

    public int getEstateFees() {
        return estateFees;
    }

    public void setEstateFees(int estateFees) {
        this.estateFees = estateFees;
    }
}
