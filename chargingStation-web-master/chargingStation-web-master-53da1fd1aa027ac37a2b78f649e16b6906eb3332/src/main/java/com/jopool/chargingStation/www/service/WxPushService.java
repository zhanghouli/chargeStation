package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.vo.WxPushVo;

/**
 * Created by synn on 2017/11/27.
 */
public interface WxPushService {

    /**
     * 金额充值通知
     * <p>
     * 充值时间
     * 充值金额
     * 赠送金额
     * 账户余额
     * 备注
     */
    void pushRechargeAmount(final WxPushVo wxPushVo, final Integer amount, final PassportAccount passportAccount);

    /**
     * 充电开始通知
     * 充电地址
     * 开始时间
     * 预付金额
     * 账户余额
     * 备注信息
     */
    void pushStartCharge(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order);

    /**
     * 充电结束通知
     * <p>
     * 充电地址
     * 结束时间
     * 消费金额
     * 退款金额
     * 账户余额
     * 备注
     */
    void pushChargeComlete(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order, final Integer account, final String remark);

    /**
     * 余量充电开始通知
     * 充电地址
     * 开始时间
     * 当前时长
     * 备注
     */
    void pushChargeTime(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order);

    /**
     * 余量充电结束通知
     * 充电地址
     * 结束时间
     * 本次充值时长
     * 退还时长
     * 剩余时长
     * 备注
     */
    void pushChargeTimeComlete(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order, final String remark);

    /**
     * 时间余量充值
     */
    void pushTimeCharge(final WxPushVo wxPushVo, final Carowner carowner);

    /**
     * 每次扣费后钱包余额通知
     * <p>
     * 本次消费金额
     * 剩余金额
     * 消费日期
     */
    void pushConsumption(final WxPushVo wxPushVo, final Integer amount, final PassportAccount passportAccount);

    /**
     * 退款余额通知
     * 退款金额
     * 退款日期
     */
    void pushRefundTips(final WxPushVo wxPushVo, final Integer amount);


    /**
     * 余额不足通知
     * 余额不足
     */
    void pushNotSufficientFunds(final WxPushVo wxPushVo);


    /**
     * 断开充电  原因各种
     * 电站名称
     * 电口序号
     * 电站地址
     * 断开原因
     * 断开时间
     */
    void pushDisconnectCharging(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final String type, final Order order);

    /**
     * 插座异常通知
     * 电站名称
     * 地址
     * 插座口
     * 异常类型
     * 时间
     */
    void pushSocketException();

    /**
     * 运营商关闭订单 通知
     * 电站名称
     * 电口
     * 时间
     * 关闭人姓名
     */
    void pushOrderClosure(final WxPushVo wxPushVo, final Passport passport, final Station station, final StationPort stationPort, final Order order);
}
