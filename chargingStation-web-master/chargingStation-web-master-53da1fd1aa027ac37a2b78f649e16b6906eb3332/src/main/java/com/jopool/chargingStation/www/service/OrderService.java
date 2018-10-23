package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.response.OrderIdResp;
import com.jopool.chargingStation.www.response.OrderListResp;
import com.jopool.chargingStation.www.vo.OrderListVo;
import com.jopool.chargingStation.www.vo.StationPortOrderVo;
import com.jopool.chargingStation.www.vo.StationPortPowerVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017年08月24日 下午6:40
 */
public interface OrderService {

    /**
     * 添加订单
     *
     * @param passport
     * @param station
     * @param stationPort
     */
    OrderIdResp addOrder(Passport passport, Station station, StationPort stationPort, ConsumePackageItem consumePackageItem, String payType, String chargeType);

    /**
     * 断开充电
     *
     * @param passportId
     * @param orderId
     * @return
     */
    Result stopRecharge(String passportId, String orderId);

    /**
     * 结束订单
     *
     * @param passportId
     * @param orderId
     * @return
     */
    Result finishOrder(String passportId, String orderId);

    /**
     * 继续充电
     *
     * @param passportId
     * @param orderId
     * @return
     */
    Result continueRecharge(String passportId, String orderId);

    /**
     * 故障上报
     *
     * @param req
     * @return
     */
    Result reportFault(StationFault req);

    /**
     * 根据订单id获取 订单 信息
     *
     * @param orderId
     * @return
     */
    Order getByOrderId(String orderId);

    /**
     * 支付成功 回调处理
     *
     * @param wxpayResponse
     * @return
     */
    boolean updateOrderPaySuccess(AlipayResponse alipayResponse, WxPayResponse wxpayResponse);

    /**
     * 获取订单列表
     *
     * @return
     */
    List<OrderListResp> getOrders(String passportId, String stationId, String status, RowBounds page);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    OrderListResp getOrderDetail(String orderId);


    /**
     * 电站 充电口  查询 power
     *
     * @param stationPortId
     * @param start
     * @param end
     * @return
     */
    List<StationPortPowerVo> listOrderByPortIdAndDate(String stationPortId, Date start, Date end);

    /**
     * 电站 id 查 order
     *
     * @param stationId
     * @return
     */
    List<Order> listOrderByStationId(String stationId);


    /**
     * 单个 电口 查找 充电 记录
     *
     * @param stationPortId
     * @param dateParam
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<StationPortOrderVo> listStationPortIdAndOrder(String stationPortId, DateParam dateParam, SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 充电时间
     * 单位：秒
     *
     * @param orderId
     * @return
     */
    int orderRechargeTime(String orderId);

    /**
     * 剩余时间
     *
     * @param orderId
     * @return
     */
    int orderRestTime(String orderId);

    /**
     * 关闭订单
     *
     * @param portId
     * @param orderId
     */
    void overTimePay(String portId, String orderId);

    /**
     * 暂时充电定时关闭订单
     *
     * @param portId
     * @param orderId
     */
    void overTimeStop(String portId, String orderId);

    /**
     * 硬件断网重连，定时关闭
     *
     * @param number
     */
    void overTimeOutWill(String number);

    /**
     * id 删除 订单
     *
     * @param orderId
     * @return
     */
    Result removeOrderId(String orderId);

    /**
     * 硬件异常情况断开充电通知服务，服务修改订单和充电口状态及其它逻辑
     *
     * @param stationId
     * @param seq
     */
    void stopRecharge(String stationId, int seq, int reportType);

    /**
     * 添加 充值订单
     *
     * @param carownerId
     * @param rechargePackageId
     * @param rechargeAmount
     */
    RechargeOrder addRechargeOrder(String carownerId, String passportId, String rechargePackageId, String orderId, int rechargeAmount, String type);

    /**
     * 获取交易订单
     *
     * @param rechargeOrderId
     * @return
     */
    RechargeOrder getRechargeOrderById(String rechargeOrderId);

    /**
     * 充电口
     *
     * @param portId
     * @return
     */
    Order getByPortIdLastTime(String portId, String status);

    /**
     * modify order
     *
     * @param order
     */
    void modifyOrder(Order order);

    /**
     * 订单列表
     *
     * @param keyword
     * @param status
     * @param page
     * @return
     */
    List<OrderListResp> orderList(String keyword, String status, DateParam dateParam, RowBounds page);


    /**
     * 订单列表 后台
     *
     * @param keyword
     * @param status
     * @param dateParam
     * @param page
     * @return
     */
    List<OrderListVo> searchOrderListVo(String keyword, String status, DateParam dateParam, RowBounds page);

    /**
     * 获取订单支付信息
     *
     * @param rechargeOrder
     * @return
     */
    String getPayBody(RechargeOrder rechargeOrder);

    /**
     * 获取订单支付标题
     *
     * @param rechargeOrder
     * @return
     */
    String getPaySubject(RechargeOrder rechargeOrder);


    /**
     * 获取 用户订单
     *
     * @param carOwnerId
     * @param status
     * @return
     */
    List<Order> searchOrderListCarOwnerId(String carOwnerId, String status);

    /**
     * 结束电口订单
     *
     * @param stationPort
     */
    void finishedOrderByStationPortId(StationPort stationPort, String remark);

    /**
     * 修改订单状态
     *
     * @param order
     * @param status
     * @param remark
     * @param modifier
     */
    void modifyOrderStatus(Order order, String status, String remark, Date endTime, String modifier);
}
