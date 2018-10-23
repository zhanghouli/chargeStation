package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Order;
import com.jopool.chargingStation.www.vo.OrderListVo;
import com.jopool.chargingStation.www.vo.StationPortOrderVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 后台订单查询
     *
     * @param keyword
     * @param status
     * @param dateParam
     * @param page
     * @return
     */
    List<OrderListVo> searchOrderListVo(@Param("keyword") String keyword, @Param("status") String status, @Param("dateParam") DateParam dateParam, RowBounds page);

    /**
     * @param stationId
     * @param status
     * @param page
     * @return
     */
    List<Order> selectByStationId(@Param("carownerId") String carownerId, @Param("stationId") String stationId, @Param("status") String status, @Param("dateParam") DateParam dateParam, RowBounds page);

    /**
     * @param stationPortId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Order> selectByPortIdAndDate(@Param("stationPortId") String stationPortId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 电站 Id 查找 order
     *
     * @param stationId
     * @return
     */
    List<Order> selectByStationIdTo(@Param("stationId") String stationId);

    /**
     * 接口最新订单
     *
     * @param stationPortId
     * @return
     */
    Order selectByPortIdLastTime(@Param("stationPortId") String stationPortId, @Param("status") String status);

    /**
     * 电口 查找 对应 充值 记录
     *
     * @param stationPortId
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<StationPortOrderVo> selectByStationPortId(@Param("stationId") String stationPortId, @Param("dateParam") DateParam dateParam, @Param("searchBaseVo") SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 车主找订单 按状态
     *
     * @param carOwnerId
     * @param status
     * @return
     */
    List<Order> selectByCarOwnerIdOrStatus(@Param("carOwnerId") String carOwnerId, @Param("status") String status);



}