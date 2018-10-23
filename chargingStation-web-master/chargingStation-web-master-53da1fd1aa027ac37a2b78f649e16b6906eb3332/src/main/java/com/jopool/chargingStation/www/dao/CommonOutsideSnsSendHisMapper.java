package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonOutsideSnsSendHis;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonOutsideSnsSendHisMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonOutsideSnsSendHis record);

    int insertSelective(CommonOutsideSnsSendHis record);

    CommonOutsideSnsSendHis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonOutsideSnsSendHis record);

    int updateByPrimaryKeyWithBLOBs(CommonOutsideSnsSendHis record);

    int updateByPrimaryKey(CommonOutsideSnsSendHis record);
}