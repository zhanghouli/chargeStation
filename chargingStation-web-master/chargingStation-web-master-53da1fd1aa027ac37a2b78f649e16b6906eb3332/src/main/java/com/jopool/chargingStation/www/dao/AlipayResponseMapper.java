package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.AlipayResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface AlipayResponseMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlipayResponse record);

    int insertSelective(AlipayResponse record);

    AlipayResponse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlipayResponse record);

    int updateByPrimaryKey(AlipayResponse record);
}