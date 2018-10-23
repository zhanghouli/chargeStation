package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.RechargeOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(RechargeOrder record);

    int insertSelective(RechargeOrder record);

    RechargeOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RechargeOrder record);

    int updateByPrimaryKey(RechargeOrder record);

    /**
     * select by orderId
     *
     * @param orderId
     * @return
     */
    RechargeOrder selectByOrderId(String orderId);
}