package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.WxPayResponse;

public interface WxPayResponseMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxPayResponse record);

    int insertSelective(WxPayResponse record);

    WxPayResponse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxPayResponse record);

    int updateByPrimaryKey(WxPayResponse record);
}