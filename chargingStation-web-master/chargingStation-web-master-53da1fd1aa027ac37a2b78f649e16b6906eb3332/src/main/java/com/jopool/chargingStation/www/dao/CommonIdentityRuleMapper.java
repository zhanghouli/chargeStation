package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonIdentityRule;

public interface CommonIdentityRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonIdentityRule record);

    int insertSelective(CommonIdentityRule record);

    CommonIdentityRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonIdentityRule record);

    int updateByPrimaryKey(CommonIdentityRule record);
}