package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.WxInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface WxInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxInfo record);

    int insertSelective(WxInfo record);

    WxInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxInfo record);

    int updateByPrimaryKey(WxInfo record);

    /**
     * select by open id
     *
     * @param openId
     * @return
     */
    WxInfo selectByOpenId(String openId);
}