package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.Station;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月01日 下午1:20
 */
public class StationAmountResp extends StationResp {
    private Integer amount;

    public StationAmountResp(Station station) {
        super(station);
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
