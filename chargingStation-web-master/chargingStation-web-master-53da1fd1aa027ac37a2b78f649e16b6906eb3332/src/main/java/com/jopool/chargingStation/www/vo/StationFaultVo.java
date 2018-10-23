package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.StationFault;
import com.jopool.jweb.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by synn on 2017/9/1.
 */
public class StationFaultVo extends StationFault {
    private List<String> picList;


    public StationFaultVo() {

    }

    public StationFaultVo(StationFault stationFault) {
        this.setId(stationFault.getId());
        this.setPassportId(stationFault.getPassportId());
        this.setStationId(stationFault.getStationId());
        this.setStationName(stationFault.getStationName());
        this.setLngE5(stationFault.getLngE5());
        this.setLatE5(stationFault.getLatE5());
        this.setAddress(stationFault.getAddress());
        this.setRemark(stationFault.getRemark());
        this.setPicList(stationFault.getPics());
        this.setIsViewed(stationFault.getIsViewed());
        this.setCreationTime(stationFault.getCreationTime());
        this.setBizDealResult(stationFault.getBizDealResult());
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(String pic) {
        List<String> strings = new ArrayList<>();
        if (!StringUtils.isEmpty(pic)) {
            for (String pics : pic.split(",")) {
                strings.add(pics);
            }
        }
        this.picList = strings;
    }
}
