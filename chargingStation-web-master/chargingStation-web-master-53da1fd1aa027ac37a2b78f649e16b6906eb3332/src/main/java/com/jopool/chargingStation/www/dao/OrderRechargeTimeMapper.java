package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.OrderRechargeTime;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRechargeTimeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderRechargeTime record);

    int insertSelective(OrderRechargeTime record);

    OrderRechargeTime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderRechargeTime record);

    int updateByPrimaryKey(OrderRechargeTime record);

    /**
     * 获取最新一条时间记录
     *
     * @return
     */
    OrderRechargeTime selectLastTime(String orderId);

    /**
     * 获取订单 充断电时间记录
     *
     * @param orderId
     * @return
     */
    List<OrderRechargeTime> selectByOrderId(String orderId);
}