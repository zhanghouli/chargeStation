package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Operator;


/**
 * Created by synn on 2017/11/10.
 */
public class AccountOpetatorOrEstate {
    private PassportOrOperatorVo passportOrOperatorVo;
    private Operator operator;
    private Integer   accountSum;
    private PassportOrEstateVo passportOrEstateVo;

    public PassportOrOperatorVo getPassportOrOperatorVo() {
        return passportOrOperatorVo;
    }

    public void setPassportOrOperatorVo(PassportOrOperatorVo passportOrOperatorVo) {
        this.passportOrOperatorVo = passportOrOperatorVo;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Integer getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(Integer accountSum) {
        this.accountSum = accountSum;
    }

    public PassportOrEstateVo getPassportOrEstateVo() {
        return passportOrEstateVo;
    }

    public void setPassportOrEstateVo(PassportOrEstateVo passportOrEstateVo) {
        this.passportOrEstateVo = passportOrEstateVo;
    }
}
