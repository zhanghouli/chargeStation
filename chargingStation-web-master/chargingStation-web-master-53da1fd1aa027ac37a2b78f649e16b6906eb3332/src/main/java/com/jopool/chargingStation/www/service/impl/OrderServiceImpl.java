package com.jopool.chargingStation.www.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.jopool.chargingStation.www.apppush.AppPushService;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.wxpay.response.TokenResp;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.constants.SystemParamKeys;
import com.jopool.chargingStation.www.dao.OrderMapper;
import com.jopool.chargingStation.www.dao.OrderRechargeTimeMapper;
import com.jopool.chargingStation.www.dao.RechargeOrderMapper;
import com.jopool.chargingStation.www.dao.StationFaultMapper;
import com.jopool.chargingStation.www.enums.*;
import com.jopool.chargingStation.www.mqtt.constants.ReportTypeEnum;
import com.jopool.chargingStation.www.response.*;
import com.jopool.chargingStation.www.schedule.OverTimePayOrderJob;
import com.jopool.chargingStation.www.schedule.OverTimeStopJob;
import com.jopool.chargingStation.www.service.*;
import com.jopool.chargingStation.www.vo.OrderListVo;
import com.jopool.chargingStation.www.vo.StationPortOrderVo;
import com.jopool.chargingStation.www.vo.StationPortPowerVo;
import com.jopool.chargingStation.www.vo.WxPushVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.exceptions.JWebException;
import com.jopool.jweb.quartz.QuartzSchedulerService;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.MathUtils;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 下午5:42
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService, SelfBeanAware<OrderService> {
    private OrderService            selfService;
    @Resource
    private StationService          stationService;
    @Resource
    private PassportService         passportService;
    @Resource
    private AccountService          accountService;
    @Resource
    private RechargePackageService  rechargePackageService;
    @Resource
    private QuartzSchedulerService  schedulerService;
    @Resource
    private CommonService           commonService;
    @Resource
    private OrderMapper             orderMapper;
    @Resource
    private OrderRechargeTimeMapper orderRechargeTimeMapper;
    @Resource
    private StationFaultMapper      stationFaultMapper;
    @Resource
    private RechargeOrderMapper     rechargeOrderMapper;
    @Resource
    private MqttProxyService        mqttProxyService;
    @Resource
    private SystemConfigService     systemConfigService;
    @Resource
    private ConsumePackageService   consumePackageService;
    @Resource
    private WxPushService           wxPushService;
    @Resource
    private AppPushService          appPushService;


    @Override
    public void setSelfBean(OrderService object) {
        this.selfService = object;
    }

    @Override
    public OrderIdResp addOrder(Passport passport, Station station, StationPort stationPort, ConsumePackageItem consumePackageItem, String payType, String chargeType) {
        Order order = null;
        //add order
        Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
        //微信推送
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);

        if (carowner != null) {
            order = new Order();
            order.setId(UUIDUtils.createId());
            order.setCarownerId(carowner.getId());
            //添加快照
            String stationSnapshotId = stationService.addOrderStationSnapshot(order, station);
            StationPortSnapshot stationPortSnapshot = stationService.addOrderStationPortSnapshot(order, stationPort);
            //充电套餐
            if (!ChargeTypeEnum.ELECTRICITY.getValue().equals(chargeType)) {
                //时间套餐
                order.setPayment(consumePackageItem.getPayment());
                order.setPayTime(consumePackageItem.getTime());
            } else {
                //电功率套餐
                order.setPayment(0);
                order.setPayTime(8 * 60);
            }
            //电功率
            order.setPower("");
            //实际结束时间 刚开始 为 0
            order.setActualTime(0);
            //物业 运营商id
            order.setEstateId(station.getEstateId());
            order.setOperatorId(station.getOperatorId());
            //
            order.setStationSnapshotId(stationSnapshotId);
            order.setStationPortSnapshotId(stationPortSnapshot.getId());
            order.setStatus(OrderStatusEnum.WAITING_FOR_PAY.getValue());
            order.setStationId(station.getId());
            order.setStationPortId(stationPort.getId());
            order.setCreator(passport.getId());
            order.setStartTime(new Date());
            order.setIsDeleted(false);
            order.setEndTime(new Date());
            order.setCreationTime(new Date());
            String rechargeOrderId = null;
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("portId", order.getStationPortId());
            dataMap.put("orderId", order.getId());
            if (ChargeTypeEnum.ELECTRICITY.getValue().equals(chargeType)) {
                if (payType.equals(PayTypeEnum.BALANCE.getValue())) {
                    order.setChargeType(ChargeTypeEnum.ELECTRICITY.getValue());
                    order.setStartTime(new Date());
                    order.setStatus(OrderStatusEnum.RECHARGING.getValue());
                    order.setPaytype(PayTypeEnum.BALANCE.getValue());
                    orderMapper.insert(order);
                    if (null != stationPort) {
                        //mqtt通知充电
                        mqttProxyService.pubSetMessage(stationPort, true, order.getPayTime(), true);
                        stationPort.setStatus(StationPortStatusEnum.WORKING.getValue());
                        stationService.modifyStationPort(stationPort);
                        //
                        //schedulerService.schedule(Constants.OVER_TIME_OUT_ORDER_JOB + order.getId(), DateUtils.addSecond(new Date(), (int) (Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.UPDATE_TIME, "30")) * 1.5)), OverTimeOutOrderJob.class, JSONArray.toJSONString(dataMap));
                        //order 充电时间记录
                        addOrderRechargeTime(order, 1);
                    }
                }
            } else {
                order.setChargeType(ChargeTypeEnum.TIME.getValue());
                //账户支付
                if (payType.equals(PayTypeEnum.BALANCE.getValue())) {
                    order.setStartTime(new Date());
                    order.setStatus(OrderStatusEnum.RECHARGING.getValue());
                    order.setPaytype(PayTypeEnum.BALANCE.getValue());
                    orderMapper.insert(order);
                    //
                    if (null != stationPort) {
                        //mqtt通知充电
                        mqttProxyService.pubSetMessage(stationPort, true, order.getPayTime(), true);
                        stationPort.setStatus(StationPortStatusEnum.WORKING.getValue());
                        stationService.modifyStationPort(stationPort);
                        //
                        //schedulerService.schedule(Constants.OVER_TIME_OUT_ORDER_JOB + order.getId(), DateUtils.addSecond(new Date(), (int) (Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.UPDATE_TIME, "30")) * 1.5)), OverTimeOutOrderJob.class, JSONArray.toJSONString(dataMap));
                        //order 充电时间记录
                        addOrderRechargeTime(order, 1);
                    }
                    //账户金额处理
                    modifyAllPassportAccount(passport, consumePackageItem.getPayment(), order, station, stationPort);
                    //账户支付 微信推送
                    //时间余量
                } else if (payType.equals(PayTypeEnum.TIMEPAY.getValue())) {
                    if (carowner.getHistoryRestTime() == null || carowner.getHistoryRestTime() - order.getPayTime() < 0) {
                        return null;
                    }
                    if (carowner.getUseStatus() != null && TimePayUseStatusEnum.USERING.getValue().equals(carowner.getUseStatus())) {
                        throw new JWebException(Code.ERROR, "其他订单正在使用中！");
                    }
                    order.setStartTime(new Date());
                    order.setStatus(OrderStatusEnum.RECHARGING.getValue());
                    order.setPaytype(PayTypeEnum.TIMEPAY.getValue());
                    orderMapper.insert(order);
                    //
                    carowner.setUseStatus(TimePayUseStatusEnum.USERING.getValue());
                    passportService.modifyCarOwner(carowner);
                    if (null != stationPort) {
                        //mqtt通知充电
                        mqttProxyService.pubSetMessage(stationPort, true, order.getPayTime(), true);
                        stationPort.setStatus(StationPortStatusEnum.WORKING.getValue());
                        stationService.modifyStationPort(stationPort);
                        //定时
                        //schedulerService.schedule(Constants.OVER_TIME_OUT_ORDER_JOB + order.getId(), DateUtils.addSecond(new Date(), (int) (Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.UPDATE_TIME, "30")) * 1.5)), OverTimeOutOrderJob.class, JSONArray.toJSONString(dataMap));
                        //order 充电时间记录
                        addOrderRechargeTime(order, 1);
                    }
                    //时间余量充值 推送
                } else {
                    //待支付
                    if (payType.equals(PayTypeEnum.WECHAT.getValue())) {
                        order.setPaytype(PayTypeEnum.WECHAT.getValue());
                    } else if (payType.equals(PayTypeEnum.ALIPAY.getValue())) {
                        order.setPaytype(PayTypeEnum.ALIPAY.getValue());
                    }
                    orderMapper.insert(order);
                    RechargeOrder rechargeOrder = selfService.addRechargeOrder(carowner.getId(), carowner.getPassportId(), null, order.getId(), consumePackageItem.getPayment(), RechargeOrderTypeEnum.ORDER.getValue());
                    rechargeOrderId = rechargeOrder.getId();
                    schedulerService.schedule(Constants.OVER_TIME_PAY_ORDER_JOB + order.getId(), DateUtils.addSecond(new Date(), 60), OverTimePayOrderJob.class, JSONArray.toJSONString(dataMap));
                }
            }
            OrderIdResp resp = new OrderIdResp();
            resp.setOrderId(order.getId());
            resp.setRechargeOrderId(rechargeOrderId);
            return resp;
        }
        return null;
    }

    private Result modifyAllPassportAccount(Passport passport, Integer payment, Order order, Station station, StationPort stationPort) {
        accountService.modifyPassportAccount(passport.getId(), -payment, PassportAccountLogTypeEnum.PAY.getValue(), order.getCreator(), "充电消费");
        ////运行商
        Operator operator = passportService.getOperatorById(station.getOperatorId());
        if (null == operator) {
            return null;
        }
        Passport operatorPassport = passportService.getById(operator.getPassportId());
        if (null == operatorPassport) {
            return null;
        }
        accountService.modifyPassportAccount(operatorPassport.getId(), (int) MathUtils.round((payment * Constants.FINANCIAL_MANAGEMENT_LEFT_PERCENT * (Double.valueOf(station.getOperatorSharingRatio()) / 100)), 0), PassportAccountLogTypeEnum.INCOME.getValue(), order.getCreator(), "充电收入，用户："
                + passport.getRealName() + "，充电站编号：" + station.getNumber() + "，充电口编号：" + stationPort.getNumber());
        //物业
        Estate estate = passportService.getByEstateId(station.getEstateId());
        if (null == estate) {
            return null;
        }
        Passport estatePassport = passportService.getById(estate.getPassportId());
        if (null == estatePassport) {
            return null;
        }
        accountService.modifyPassportAccount(estatePassport.getId(), (int) MathUtils.round((payment * Constants.FINANCIAL_MANAGEMENT_LEFT_PERCENT * (Double.valueOf(station.getEstateSharingRatio()) / 100)), 0), PassportAccountLogTypeEnum.INCOME.getValue(), order.getCreator(), "充电收入，用户："
                + passport.getRealName() + "，充电站编号：" + station.getNumber() + "，充电口编号：" + stationPort.getNumber());
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result stopRecharge(String passportId, String stationPortIds) {
        String[] stationPortIdStr = stationPortIds.split(",", -1);
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        for (int i = 0; i < stationPortIdStr.length; i++) {
            Passport passport = passportService.getById(passportId);
            if (null == passport) {
                return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
            }
            Order order = orderMapper.selectByPortIdLastTime(stationPortIdStr[i], OrderStatusEnum.RECHARGING.getValue());
            if (order == null) {
                return new Result(Code.ERROR, "订单不存在或者已充电口已暂停");
            }
            if (!order.getStatus().equals(OrderStatusEnum.RECHARGING.getValue())) {
                return new Result(Code.ERROR, CodeMessage.ORDER_STATUS_FALUT);
            }
            StationPort stationPort = stationService.getPortById(stationPortIdStr[i]);
            if (stationPort == null) {
                return new Result(Code.ERROR, CodeMessage.STATION_PORT_FAULT);
            }
            if (StationPortStatusEnum.DISABLE.getValue().equals(stationPort.getStatus()) || StationPortStatusEnum.FAULT.equals(stationPort.getStatus())) {
                return new Result(Code.SUCCESS);
            }
            //mqtt通知充电
            mqttProxyService.pubSetMessage(stationPort, false, 0, true);
            //订单状态
            order.setStatus(OrderStatusEnum.WAITING_FOR_CONNECT.getValue());
            order.setModifier(passportId);
            orderMapper.updateByPrimaryKeySelective(order);
            //定时长时间未连接，。关闭订单
            int waitTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.STOP_WAIT_TIME, "10"));
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("portId", stationPort.getId());
            dataMap.put("orderId", order.getId());
            schedulerService.schedule(Constants.OVER_TIME_STOP_JOB + order.getId(), DateUtils.addMinute(new Date(), waitTime), OverTimeStopJob.class, JSONArray.toJSONString(dataMap));
            //订单时间记录
            OrderRechargeTime orderRechargeTime = orderRechargeTimeMapper.selectLastTime(order.getId());
            orderRechargeTime.setEndTime(new Date());
            //剩余时间
            long time = (orderRechargeTime.getEndTime().getTime() - orderRechargeTime.getStartTime().getTime()) / 1000;
            orderRechargeTime.setTime((int) time);
            orderRechargeTime.setModifier(passportId);
            orderRechargeTimeMapper.updateByPrimaryKeySelective(orderRechargeTime);
            stationPort.setStatus(StationPortStatusEnum.SUSPEND.getValue());
            stationService.modifyStationPort(stationPort);
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result finishOrder(String passportId, String portIds) {
        String[] portIdStr = portIds.split(",", -1);
        //运营商
        Passport passport = passportService.getById(passportId);
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        for (int i = 0; i < portIdStr.length; i++) {
            //充电口
            StationPort stationPort = stationService.getPortById(portIdStr[i]);
            //订单
            Order order = orderMapper.selectByPortIdLastTime(portIdStr[i], null);
            if (order == null || OrderStatusEnum.FINISHED.getValue().equals(order.getStatus()) || OrderStatusEnum.CLOSE.getValue().equals(order.getStatus())) {
                return new Result(Code.ERROR, CodeMessage.ORDER_NOT_EXIST);
            }
            //
            if (null != stationPort) {
                //mqtt通知充电
                mqttProxyService.pubSetMessage(stationPort, false, 0, true);
            }
            if (null != stationPort && !StationPortStatusEnum.DISABLE.getValue().equals(stationPort.getStatus()) && !StationPortStatusEnum.FAULT.getValue().equals(stationPort.getStatus())) {
                stationPort.setStatus(StationPortStatusEnum.FREE.getValue());
                stationService.modifyStationPort(stationPort);
            }
            //订单时间记录
            OrderRechargeTime orderRechargeTime = orderRechargeTimeMapper.selectLastTime(order.getId());
            orderRechargeTime.setEndTime(new Date());
            long time = (orderRechargeTime.getEndTime().getTime() - orderRechargeTime.getStartTime().getTime()) / 1000;
            orderRechargeTime.setTime((int) time);
            orderRechargeTime.setModifier(passportId);
            orderRechargeTimeMapper.updateByPrimaryKeySelective(orderRechargeTime);
            //总时间获取
            List<OrderRechargeTime> orderRechargeTimes = orderRechargeTimeMapper.selectByOrderId(order.getId());
            int allTime = 0;
            for (OrderRechargeTime t : orderRechargeTimes) {
                allTime += t.getTime();
            }
            //订单状态
            order.setEndTime(new Date());
            order.setActualTime(allTime / 60);
            order.setStatus(OrderStatusEnum.FINISHED.getValue());
            order.setRemark("运行商管理客户端关闭订单");
            order.setModifier(passportId);
            orderMapper.updateByPrimaryKeySelective(order);
            Carowner carowner = passportService.getCarownerById(order.getCarownerId());
            Station station = stationService.getById(order.getStationId());
            if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
                int totalCost = 0;
                int hours = order.getActualTime() / 60;
                if (order.getActualTime() % 60 != 0) {
                    hours = hours + 1;
                }
                if (!StringUtils.isEmpty(order.getPower())) {
                    ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageByPow(order.getPower());
                    if (chargeCostPackage != null && chargeCostPackage.getCost() != null) {
                        totalCost = hours * chargeCostPackage.getCost();
                    }
                }
                if (null != station) {
                    if (totalCost != 0) {
                        //Carowner carowner = passportService.getCarownerById(order.getCarownerId());
                        if (carowner != null) {
                            Passport carownerPassport = passportService.getById(carowner.getPassportId());
                            modifyAllPassportAccount(carownerPassport, totalCost, order, station, stationPort);
                            order.setPayment(totalCost);
                            order.setActualPayment(totalCost);
                            selfService.modifyOrder(order);
                        }
                    }
                }
                pushWx(MessageTemplateEnum.CHARGE_COMPLETE.getValue(), "运营商结束订单", totalCost, station, stationPort, order, carowner);
            } else {
                //使用时间余量支付
                if (PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
                    carowner.setHistoryRestTime(carowner.getHistoryRestTime() - order.getActualTime() >= 0 ? carowner.getHistoryRestTime() - order.getActualTime() : 0);
                    carowner.setUseStatus(TimePayUseStatusEnum.NOT_USED.getValue());
                    passportService.modifyCarOwner(carowner);
                }
                //多余时间充值 历史充电时间余量
                int restTime = order.getPayTime() - order.getActualTime();
                if (!PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype()) && restTime > 0) {
                    if (carowner != null) {
                        carowner.setHistoryRestTime(carowner.getHistoryRestTime() == null ? restTime : restTime + carowner.getHistoryRestTime());
                        passportService.modifyCarOwner(carowner);
                    }
                }
                //订单关闭推送
                pushWx(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue(), "运营商关闭订单", 0, station, stationPort, order, carowner);
            }
            //微信 运营商结束 订单通知
        }
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result continueRecharge(String passportId, String orderId) {
        Passport passport = passportService.getById(passportId);
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order) {
            return new Result(Code.ERROR, CodeMessage.ORDER_NOT_EXIST);
        }
        if (!order.getStatus().equals(OrderStatusEnum.WAITING_FOR_CONNECT.getValue())) {
            return new Result(Code.ERROR, CodeMessage.ORDER_STATUS_FALUT);
        }
        //充电口
        StationPort stationPort = stationService.getPortById(order.getStationPortId());
        if (stationPort == null) {
            return new Result(Code.ERROR, CodeMessage.STATION_PORT_FAULT);
        }
        if (StationPortStatusEnum.DISABLE.getValue().equals(stationPort.getStatus()) || StationPortStatusEnum.FAULT.equals(stationPort.getStatus())) {
            return new Result(Code.SUCCESS);
        }
        //mqtt通知充电
        mqttProxyService.pubSetMessage(stationPort, true, orderRestTime(orderId) / 60, true);
        //取消定时
        schedulerService.removeSchedule(Constants.OVER_TIME_PAUSE_JOB + "_" + stationPort.getStationId() + "_" + stationPort.getSeq());
        //订单状态
        order.setStatus(OrderStatusEnum.RECHARGING.getValue());
        order.setModifier(passportId);
        orderMapper.updateByPrimaryKeySelective(order);
        stationPort.setStatus(StationPortStatusEnum.WORKING.getValue());
        stationService.modifyStationPort(stationPort);
        //order 充电时间记录
        addOrderRechargeTime(order, 2);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result reportFault(StationFault stationFault) {
        stationFaultMapper.insertSelective(stationFault);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Order getByOrderId(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public boolean updateOrderPaySuccess(AlipayResponse alipayResponse, WxPayResponse wxpayResponse) {
        //订单状态更新
        RechargeOrder rechargeOrder = new RechargeOrder();
        String remark = "";
        String outTradeNo = "";
        if (alipayResponse != null) {
            remark = "账户支付宝充值";
            outTradeNo = alipayResponse.getOutTradeNo();
        } else if (wxpayResponse != null) {
            remark = "账户微信充值";
            outTradeNo = wxpayResponse.getOutTradeNo();
        }
        rechargeOrder = rechargeOrderMapper.selectByPrimaryKey(outTradeNo);
        if (RechargeOrderStatusEnum.PAYED.getValue().equals(rechargeOrder.getStatus())) {
            return true;
        }
        Passport passport = passportService.getById(rechargeOrder.getCreator());

        rechargeOrder.setStatus(RechargeOrderStatusEnum.PAYED.getValue());
        rechargeOrderMapper.updateByPrimaryKeySelective(rechargeOrder);
        //微信凭证
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
        PassportAccount passportAccount = passportService.getPassportAmountByPassportId(carowner.getPassportId());
        if ((rechargeOrder.getType().equals(RechargeOrderTypeEnum.ORDER.getValue()))) {
//            accountService.modifyPassportAccount(passport, rechargeOrder.getAmount(), PassportAccountLogTypeEnum.RECHARGE.getValue(), rechargeOrder.getCreator(), "账户充值");
            Order order = selfService.getByOrderId(rechargeOrder.getOrderId());
            if (null == order) {
                return false;
            }
            //直接充电
            Station station = stationService.getById(order.getStationId());
            if (null == station) {
                return false;
            }
            StationPort stationPort = stationService.getPortById(order.getStationPortId());
            if (null == stationPort) {
                return false;
            }
            //用户
//            accountService.modifyPassportAccount(passport.getId(), 0, PassportAccountLogTypeEnum.PAY.getValue(), rechargeOrder.getCreator(), "电站充电");
            //运行商
            Operator operator = passportService.getOperatorById(station.getOperatorId());
            if (null == operator) {
                return false;
            }
            Passport operatorPassport = passportService.getById(operator.getPassportId());
            if (null == operatorPassport) {
                return false;
            }
            //物业
            Estate estate = passportService.getByEstateId(station.getEstateId());
            if (null == estate) {
                return false;
            }
            Passport estatePassport = passportService.getById(estate.getPassportId());
            if (null == estatePassport) {
                return false;
            }
            order.setStartTime(new Date());
            order.setStatus(OrderStatusEnum.RECHARGING.getValue());
            selfService.modifyOrder(order);
            //mqtt通知充电
            mqttProxyService.pubSetMessage(stationPort, true, order.getPayTime(), true);
            Order orderNew = orderMapper.selectByPrimaryKey(order.getId());
            if (OrderStatusEnum.FINISHED.getValue().equals(orderNew.getStatus())) {
                return true;
            }
            accountService.modifyPassportAccount(operatorPassport.getId(), (int) MathUtils.round((rechargeOrder.getPayment() * Constants.FINANCIAL_MANAGEMENT_LEFT_PERCENT * (Double.valueOf(station.getOperatorSharingRatio()) / 100)), 0), PassportAccountLogTypeEnum.INCOME.getValue(), rechargeOrder.getCreator(), "充电收入，用户："
                    + passport.getRealName() + "，充电站编号：" + station.getNumber() + "，充电口编号：" + stationPort.getNumber());
            accountService.modifyPassportAccount(estatePassport.getId(), (int) MathUtils.round((rechargeOrder.getPayment() * Constants.FINANCIAL_MANAGEMENT_LEFT_PERCENT * (Double.valueOf(station.getEstateSharingRatio()) / 100)), 0), PassportAccountLogTypeEnum.INCOME.getValue(), rechargeOrder.getCreator(), "充电收入，用户："
                    + passport.getRealName() + "，充电站编号：" + station.getNumber() + "，充电口编号：" + stationPort.getNumber());
            //系统
            accountService.modifyPassportAccount(Constants.DEFAULT_PROPERTY_ID, rechargeOrder.getPayment(), PassportAccountLogTypeEnum.INCOME.getValue(), rechargeOrder.getCreator(), "充电收入，用户："
                    + passport.getRealName() + "，充电站编号：" + station.getNumber() + "，充电口编号：" + stationPort.getNumber());
            stationPort.setStatus(StationPortStatusEnum.WORKING.getValue());
            stationService.modifyStationPort(stationPort);
            //
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("portId", order.getStationPortId());
            dataMap.put("orderId", order.getId());
            //schedulerService.schedule(Constants.OVER_TIME_OUT_ORDER_JOB + order.getId(), DateUtils.addSecond(new Date(), (int) (Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.UPDATE_TIME, "30")) * 1.5)), OverTimeOutOrderJob.class, JSONArray.toJSONString(dataMap));
            //order 充电时间记录
            addOrderRechargeTime(order, 1);
            //用户 支付宝或微信  支付充值
            if (carowner != null) {
                MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.START_CHARGE.getValue());
                if (messageTemplate != null && tokenResp != null) {
                    WxPushVo wxPushVo = new WxPushVo(tokenResp, messageTemplate, carowner, passportAccount);
                    //开单
                    wxPushService.pushStartCharge(wxPushVo, station, stationPort, order);
                }
                //个推
                if (!StringUtils.isEmpty(carowner.getClientId())) {
                    AppPushMessage appPushMessage = new AppPushMessage("充电开始通知", "充电详情", "http://h5.h1d.com.cn/station/index.html#/member/chaRecDetail?orderId=" + order.getId());
                    appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
                }
            }

        } else {
            //个推信息
            AppPushMessage appPushMessage = new AppPushMessage("金额充值通知", "充值详情", "http://h5.h1d.com.cn/station/index.html#/member/walletRecord");
            accountService.modifyPassportAccount(passport.getId(), rechargeOrder.getAmount(), PassportAccountLogTypeEnum.RECHARGE.getValue(), rechargeOrder.getCreator(), remark);
            //系统
            accountService.modifyPassportAccount(Constants.SYSTEM_ID, rechargeOrder.getPayment(), PassportAccountLogTypeEnum.INCOME.getValue(), rechargeOrder.getCreator(), remark);
            //微信 充值提醒
            if (carowner != null) {
                MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.RECHARGE_AMOUNT.getValue());
                if (messageTemplate != null && tokenResp != null) {
                    WxPushVo wxPushVo = new WxPushVo(tokenResp, messageTemplate, carowner);
                    wxPushService.pushRechargeAmount(wxPushVo, rechargeOrder.getAmount(), passportAccount);
                }

            }
            //个推
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
            }
        }

        return true;
    }

    @Override
    public List<OrderListResp> getOrders(String passportId, String stationId, String status, RowBounds page) {
        List<OrderListResp> resps = new ArrayList<OrderListResp>();
        Carowner carowner = passportService.getCarownerByPassportId(passportId);
        if (carowner != null) {
            List<Order> orders = orderMapper.selectByStationId(carowner.getId(), stationId, status, new DateParam(), page);
            for (Order order : orders) {
                OrderListResp resp = new OrderListResp();
                OrderResp orderResp = new OrderResp(order);
                Station station = stationService.searchByStationId(order.getStationId());
                //电站
                StationResp stationResp = null;
                if (null != station) {
                    stationResp = new StationResp(station);
                }
                StationPort stationPort = stationService.getPortById(order.getStationPortId());
                //接口
                StationPortResp stationPortResp = null;
                if (null != stationPort) {
                    stationPortResp = new StationPortResp(stationPort);
                }
                resp.setOrder(orderResp);
                resp.setStation(stationResp);
                resp.setPort(stationPortResp);
                resps.add(resp);
            }
        }
        return resps;
    }

    @Override
    public OrderListResp getOrderDetail(String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        OrderListResp resp = new OrderListResp();
        OrderResp orderResp = new OrderResp(order);
        if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
            if (!StringUtils.isEmpty(order.getPower())) {
                ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageByPow(order.getPower());
                if (chargeCostPackage != null && chargeCostPackage.getCost() != null) {
                    orderResp.setCost(chargeCostPackage.getCost());
                }
            } else {
                orderResp.setCost(0);
            }
        }
        RechargeOrder rechargeOrder = rechargeOrderMapper.selectByOrderId(order.getId());
        if (null != rechargeOrder) {
            orderResp.setRechargeOrderId(rechargeOrder.getId());
        }
//        if (!ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
        orderResp.setRestTime(selfService.orderRestTime(orderId));
//        }
        Station station = stationService.searchByStationId(order.getStationId());
        //电站
        StationResp stationResp = null;
        if (null != station) {
            stationResp = new StationResp(station);
        }
        StationPort stationPort = stationService.getPortById(order.getStationPortId());
        //接口
        StationPortResp stationPortResp = null;
        if (null != stationPort) {
            stationPortResp = new StationPortResp(stationPort);
        }
        if (order.getStatus().equals(OrderStatusEnum.RECHARGING.getValue())) {
            StationPortRealTimeListen listens = stationService.getStationPortRealTimeListenLastTimeByPortId(stationPort.getId());
            Double cyclingDistance = 0.0;
            if (listens != null && !StringUtils.isEmpty(listens.getEnergy())) {
                cyclingDistance = MathUtils.div((20000 * Double.valueOf(listens.getEnergy())), 350);
            }
            orderResp.setCyclingDistance(cyclingDistance + "");
        }
        resp.setOrder(orderResp);
        resp.setStation(stationResp);
        resp.setPort(stationPortResp);
        return resp;
    }

    @Override
    public List<StationPortPowerVo> listOrderByPortIdAndDate(String stationPortId, Date start, Date end) {
        //返回 每日 消耗 度数
        List<StationPortPowerVo> stationPortPowerVos = new ArrayList<StationPortPowerVo>();
        List<DateParam> dateParamList = new ArrayList<DateParam>();
        if (start == null && end == null) {
            //查询日期 数组
            dateParamList = listDateParam(new Date());
            //获取每日消耗电量数组
            stationPortPowerVos = listPortPowerVo(stationPortId, dateParamList);
        } else {
            //日期搜索查询
            if (start == null) {
                //end 之前 15天
                dateParamList = listDateParam(end);
                //获取每日消耗电量数组
                stationPortPowerVos = listPortPowerVo(stationPortId, dateParamList);
            } else if (end == null) {
                //start 之后 15天
                dateParamList = listDateParamStart(start);
                //获取每日消耗电量数组
                stationPortPowerVos = listPortPowerVo(stationPortId, dateParamList);
            } else {
                //搜索日期 都有
                dateParamList = listStartEndTime(start, end);
                //获取每日消耗电量数组
                stationPortPowerVos = listPortPowerVo(stationPortId, dateParamList);
            }
        }
        return stationPortPowerVos;
    }

    @Override
    public List<Order> listOrderByStationId(String stationId) {
        return orderMapper.selectByStationIdTo(stationId);
    }

    @Override
    public List<StationPortOrderVo> listStationPortIdAndOrder(String stationPortId, DateParam dateParam, SearchBaseVo searchBaseVo, RowBounds page) {
        return orderMapper.selectByStationPortId(stationPortId, dateParam, searchBaseVo, page);
    }

    @Override
    public Result removeOrderId(String orderId) {
        orderMapper.deleteByPrimaryKey(orderId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public void stopRecharge(String stationId, int seq, int reportType) {
        Station station = stationService.searchByStationId(stationId);
        StationPort stationPort = stationService.getStationPortByStationIdAndSeq(stationId, seq);
        //个推信息
        AppPushMessage appPushMessage = null;
        //插头被拔出情况，暂停充电口，其他情况附空
        if (null != stationPort) {
            stationPort.setStatus(ReportTypeEnum.CLOSE_PULL_OUT.getValue() == reportType ? StationPortStatusEnum.SUSPEND.getValue() : StationPortStatusEnum.FREE.getValue());
            stationService.modifyStationPort(stationPort);
        }
        //订单待连接状态
        Order order = selfService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.RECHARGING.getValue());
        if (null == order) {
            order = selfService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.WAITING_FOR_CONNECT.getValue());
        }
        if (order != null) {
            //订单时间记录
            order.setActualTime(getActuallyTime(order));
            //车主
            Carowner carowner = passportService.getCarownerById(order.getCarownerId());
            OrderStatusEnum orderStatus = OrderStatusEnum.CLOSE;
            if (ReportTypeEnum.CLOSE_MIN_POWER.getValue() == reportType || ReportTypeEnum.CLOSE_MAX_POWER.getValue() == reportType || ReportTypeEnum.CLOSE_NORMAL.getValue() == reportType) {
                orderStatus = OrderStatusEnum.FINISHED;
                //按电量充值
                if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
                    electricityFinishOrder(order, carowner, stationPort, reportType);
                    pushWx(MessageTemplateEnum.CHARGE_COMPLETE.getValue(), ReportTypeEnum.getRoportTypeName(reportType), order.getActualPayment(), station, stationPort, order, carowner);
                } else {
                    //使用时间余量
                    if (PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
                        carowner.setHistoryRestTime(carowner.getHistoryRestTime() - order.getActualTime() >= 0 ? carowner.getHistoryRestTime() - order.getActualTime() : 0);
                        carowner.setUseStatus(TimePayUseStatusEnum.NOT_USED.getValue());
                        passportService.modifyCarOwner(carowner);
                    }
                    pushWx(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue(), ReportTypeEnum.getRoportTypeName(reportType), 0, station, stationPort, order, carowner);
                }
            } else if (ReportTypeEnum.CLOSE_PULL_OUT.getValue() == reportType) {
                orderStatus = OrderStatusEnum.WAITING_FOR_CONNECT;
            }

            //如果是插座未连接或者设定开关状态失败 需要退剩余时间 完成订单
            if (ReportTypeEnum.CLOSE_WAIT_TIMEOUT.getValue() == reportType || ReportTypeEnum.SET_FAIL.getValue() == reportType) {
                orderStatus = OrderStatusEnum.FINISHED;
                if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
                    electricityFinishOrder(order, carowner, stationPort, reportType);
                    pushWx(MessageTemplateEnum.CHARGE_COMPLETE.getValue(), ReportTypeEnum.getRoportTypeName(reportType), order.getActualPayment(), station, stationPort, order, carowner);
                } else {
                    //使用时间余量
                    int restTime = order.getPayTime();
                    if (PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
                        carowner.setHistoryRestTime(carowner.getHistoryRestTime() - order.getActualTime() >= 0 ? carowner.getHistoryRestTime() - order.getActualTime() : 0);
                        carowner.setUseStatus(TimePayUseStatusEnum.NOT_USED.getValue());
                        passportService.modifyCarOwner(carowner);
                    } else if (restTime > 0) {
                        //多余时间充值 历史充电时间余量 返回所有时间
                        if (carowner != null) {
                            carowner.setHistoryRestTime(carowner.getHistoryRestTime() == null ? restTime : restTime + carowner.getHistoryRestTime());
                            passportService.modifyCarOwner(carowner);
                        }
                    }
                }
            }
            //remark
            if (ReportTypeEnum.CLOSE_MIN_POWER.getValue() == reportType) {
                //小于下限功率涓流结束关闭
                order.setRemark(ReportTypeEnum.CLOSE_MIN_POWER.getName());
            } else if (ReportTypeEnum.CLOSE_MAX_POWER.getValue() == reportType) {
                //大于上限功率结束关闭
                order.setRemark(ReportTypeEnum.CLOSE_MAX_POWER.getName());
            } else if (ReportTypeEnum.CLOSE_WAIT_TIMEOUT.getValue() == reportType) {
                //通电后一定时间没有插入插头自动关闭
                order.setRemark(ReportTypeEnum.CLOSE_WAIT_TIMEOUT.getName());
                pushWx(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue(), "通电后一定时间没有插入插头自动关闭", 0, station, stationPort, order, carowner);
            } else if (ReportTypeEnum.SET_FAIL.getValue() == reportType) {
                //设定开关状态失败
                order.setRemark(ReportTypeEnum.SET_FAIL.getName());
                pushWx(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue(), "未成功开启充电口", 0, station, stationPort, order, carowner);
            } else if (ReportTypeEnum.CLOSE_NORMAL.getValue() == reportType) {
                //充值完成
                order.setRemark(ReportTypeEnum.CLOSE_NORMAL.getName());
            }
            order.setEndTime(new Date());
            order.setStatus(orderStatus.getValue());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }

    private int getActuallyTime(Order order) {
        OrderRechargeTime orderRechargeTime = orderRechargeTimeMapper.selectLastTime(order.getId());
        orderRechargeTime.setEndTime(new Date());
        long time = (orderRechargeTime.getEndTime().getTime() - orderRechargeTime.getStartTime().getTime()) / 1000;
        orderRechargeTime.setTime((int) time);
        orderRechargeTime.setModifier(order.getCreator());
        orderRechargeTimeMapper.updateByPrimaryKeySelective(orderRechargeTime);
        //总时间获取
        List<OrderRechargeTime> orderRechargeTimes = orderRechargeTimeMapper.selectByOrderId(order.getId());
        int allTime = 0;
        for (OrderRechargeTime t : orderRechargeTimes) {
            allTime += t.getTime();
        }
        return allTime / 60;
    }

    private void electricityFinishOrder(Order order, Carowner carowner, StationPort stationPort, int reportType) {
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        int totalCost = 0;
        int hours = order.getActualTime() / 60;
        if (order.getActualTime() % 60 != 0) {
            hours = hours + 1;
        }
        //价格花费
        if (!StringUtils.isEmpty(order.getPower())) {
            ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageByPow(order.getPower());
            if (chargeCostPackage != null && chargeCostPackage.getCost() != null) {
                totalCost = hours * chargeCostPackage.getCost();
            }
        }
        Station station = stationService.getById(order.getStationId());
        if (null != station) {
            if (totalCost != 0) {
                if (carowner != null) {
                    Passport carownerPassport = passportService.getById(carowner.getPassportId());
                    modifyAllPassportAccount(carownerPassport, totalCost, order, station, stationPort);
                    order.setPayment(totalCost);
                    order.setActualPayment(totalCost);
                    orderMapper.updateByPrimaryKeySelective(order);
                }
            }
        }
    }

    @Override
    public RechargeOrder addRechargeOrder(String carownerId, String passportId, String rechargePackageId, String orderId, int rechargeAmount, String type) {
        RechargeOrder rechargeOrder = new RechargeOrder();
        if (type.equals(RechargeOrderTypeEnum.RECHARGE_PACKAGE.getValue())) {
            RechargePackage rechargePackage = rechargePackageService.getById(rechargePackageId);
            RechargePackageSnapshot rechargePackageSnapshot = rechargePackageService.addRechargePackageSnapshot(rechargePackage, rechargeAmount);
            rechargeOrder.setRchargePackageSnapshotId(rechargePackageSnapshot.getId());
            rechargeOrder.setPayment(rechargeAmount);
            if (rechargeOrder != null) {
                rechargeOrder.setAmount(rechargePackageSnapshot.getAmount());
            }
        } else {
            rechargeOrder.setOrderId(orderId);
            rechargeOrder.setPayment(rechargeAmount);
            rechargeOrder.setAmount(rechargeAmount);
        }

        rechargeOrder.setId(UUIDUtils.createId());
        rechargeOrder.setCarownerId(carownerId);
        rechargeOrder.setStatus(RechargeOrderStatusEnum.WAITING_FOR_PAY.getValue());
        rechargeOrder.setType(type);
        rechargeOrder.setCreator(passportId);
        rechargeOrderMapper.insertSelective(rechargeOrder);
        return rechargeOrder;
    }

    @Override
    public RechargeOrder getRechargeOrderById(String rechargeOrderId) {
        return rechargeOrderMapper.selectByPrimaryKey(rechargeOrderId);
    }

    @Override
    public Order getByPortIdLastTime(String portId, String status) {
        return orderMapper.selectByPortIdLastTime(portId, status);
    }

    @Override
    public void modifyOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public List<OrderListResp> orderList(String keyword, String status, DateParam dateParam, RowBounds page) {
        List<OrderListResp> resps = new ArrayList<OrderListResp>();
        List<Order> orders = orderMapper.selectByStationId(null, null, status, dateParam, page);
        for (Order order : orders) {
            OrderListResp resp = new OrderListResp();
            OrderResp orderResp = new OrderResp(order);
            Station station = stationService.searchByStationId(order.getStationId());
            //电站
            StationResp stationResp = null;
            if (null != station) {
                stationResp = new StationResp(station);
            }
            StationPort stationPort = stationService.getPortById(order.getStationPortId());
            //接口
            StationPortResp stationPortResp = null;
            if (null != stationPort) {
                stationPortResp = new StationPortResp(stationPort);
            }
            resp.setOrder(orderResp);
            resp.setStation(stationResp);
            resp.setPort(stationPortResp);
            Passport passport = passportService.getById(order.getCreator());
            if (passport != null) {
                resp.setCarownerName(passport.getUserName());
                resp.setCarownerPhone(passport.getPhone());
            }
            resps.add(resp);
        }
        return resps;
    }

    @Override
    public List<OrderListVo> searchOrderListVo(String keyword, String status, DateParam dateParam, RowBounds page) {
        return orderMapper.searchOrderListVo(keyword, status, dateParam, page);
    }

    @Override
    public String getPayBody(RechargeOrder rechargeOrder) {
        String str = "";
        if (RechargeOrderTypeEnum.RECHARGE_PACKAGE.getValue().equals(rechargeOrder.getType())) {
            str = "套餐充值，充" + MathUtils.div(rechargeOrder.getPayment(), 100, 2) + "得" + MathUtils.div(rechargeOrder.getAmount(), 100, 2);
        } else if (RechargeOrderTypeEnum.ORDER.getValue().equals(rechargeOrder.getType())) {
            Order order = orderMapper.selectByPrimaryKey(rechargeOrder.getOrderId());
            if (order != null) {
                str = "电站充电";
                Station station = stationService.getById(order.getStationId());
                StationPort stationPort = stationService.getPortById(order.getStationPortId());
                if (station != null && stationPort != stationPort) {
                    str += "，电站编号" + station.getNumber() + "，电口编号" + stationPort.getNumber();
                }
            }
        }
        return str;
    }

    @Override
    public String getPaySubject(RechargeOrder rechargeOrder) {
        String str = "";
        if (RechargeOrderTypeEnum.RECHARGE_PACKAGE.getValue().equals(rechargeOrder.getType())) {
            str = "账户套餐充值";
        } else if (RechargeOrderTypeEnum.ORDER.getValue().equals(rechargeOrder.getType())) {
            str = "电站充电付款";
        }
        return str;
    }

    @Override
    public List<Order> searchOrderListCarOwnerId(String carOwnerId, String status) {
        return orderMapper.selectByCarOwnerIdOrStatus(carOwnerId, status);
    }

    @Override
    public void finishedOrderByStationPortId(StationPort stationPort, String remark) {
        Order order = orderMapper.selectByPortIdLastTime(stationPort.getId(), null);
        if (order == null || OrderStatusEnum.FINISHED.getValue().equals(order.getStatus()) || OrderStatusEnum.CLOSE.getValue().equals(order.getStatus())) {
            return;
        }
        //订单时间记录
        OrderRechargeTime orderRechargeTime = orderRechargeTimeMapper.selectLastTime(order.getId());
        orderRechargeTime.setEndTime(new Date());
        long time = (orderRechargeTime.getEndTime().getTime() - orderRechargeTime.getStartTime().getTime()) / 1000;
        orderRechargeTime.setTime((int) time);
        orderRechargeTime.setModifier(Constants.SYSTEM_ID);
        orderRechargeTimeMapper.updateByPrimaryKeySelective(orderRechargeTime);
        //总时间获取
        List<OrderRechargeTime> orderRechargeTimes = orderRechargeTimeMapper.selectByOrderId(order.getId());
        int allTime = 0;
        for (OrderRechargeTime t : orderRechargeTimes) {
            allTime += t.getTime();
        }
        //订单状态
        order.setEndTime(new Date());
        order.setActualTime(allTime / 60);
        order.setStatus(OrderStatusEnum.FINISHED.getValue());
        order.setRemark(remark);
        order.setModifier(Constants.SYSTEM_ID);
        orderMapper.updateByPrimaryKeySelective(order);
        Carowner carowner = passportService.getCarownerById(order.getCarownerId());
        Station station = stationService.getById(order.getStationId());
        if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
            int totalCost = 0;
            int hours = order.getActualTime() / 60;
            if (order.getActualTime() % 60 != 0) {
                hours = hours + 1;
            }
            if (!StringUtils.isEmpty(order.getPower())) {
                ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageByPow(order.getPower());
                if (chargeCostPackage != null && chargeCostPackage.getCost() != null) {
                    totalCost = hours * chargeCostPackage.getCost();
                }
            }
            if (null != station) {
                if (totalCost != 0) {
                    //Carowner carowner = passportService.getCarownerById(order.getCarownerId());
                    if (carowner != null) {
                        Passport carownerPassport = passportService.getById(carowner.getPassportId());
                        modifyAllPassportAccount(carownerPassport, totalCost, order, station, stationPort);
                        order.setPayment(totalCost);
                        order.setActualPayment(totalCost);
                        selfService.modifyOrder(order);
                    }
                }
            }
            pushWx(MessageTemplateEnum.CHARGE_COMPLETE.getValue(), remark, totalCost, station, stationPort, order, carowner);
        } else {
            //使用时间余量支付
            if (PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
                carowner.setHistoryRestTime(carowner.getHistoryRestTime() - order.getActualTime() >= 0 ? carowner.getHistoryRestTime() - order.getActualTime() : 0);
                carowner.setUseStatus(TimePayUseStatusEnum.NOT_USED.getValue());
                passportService.modifyCarOwner(carowner);
            }
            //多余时间充值 历史充电时间余量
            int restTime = order.getPayTime() - order.getActualTime();
            if (!PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype()) && restTime > 0) {
                if (carowner != null) {
                    carowner.setHistoryRestTime(carowner.getHistoryRestTime() == null ? restTime : restTime + carowner.getHistoryRestTime());
                    passportService.modifyCarOwner(carowner);
                }
            }
            //订单关闭推送
            pushWx(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue(), remark, 0, station, stationPort, order, carowner);
        }
        //微信 运营商结束 订单通知
    }

    @Override
    public void modifyOrderStatus(Order order, String status, String remark, Date endTime, String modifier) {
        order.setStatus(status);
        order.setRemark(remark);
        if (endTime != null) {
            order.setEndTime(endTime);
        }
        if (!StringUtils.isEmpty(modifier)) {
            order.setModifier(modifier);
        }
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public int orderRechargeTime(String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        int allTime = 0;
        if (null != order) {
            List<OrderRechargeTime> rechargeTimes = orderRechargeTimeMapper.selectByOrderId(orderId);
            for (OrderRechargeTime rechargeTime : rechargeTimes) {
                if (rechargeTime.getTime() != null) {
                    allTime += rechargeTime.getTime();
                }
            }
            OrderRechargeTime orderRechargeTime = orderRechargeTimeMapper.selectLastTime(orderId);
            if (null != orderRechargeTime && orderRechargeTime.getEndTime() == null) {
                orderRechargeTime.setEndTime(new Date());
                long time = (orderRechargeTime.getEndTime().getTime() - orderRechargeTime.getStartTime().getTime()) / 1000;
                allTime += (int) time;
            }
        }
        return allTime;
    }

    @Override
    public int orderRestTime(String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null) {
            //已充电时间  单位秒
            int allTime = selfService.orderRechargeTime(orderId);
            int restTime = (order.getPayTime() * 60 - allTime);
            return restTime;
        }
        return 0;
    }

    @Override
    public void overTimePay(String portId, String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null != order) {
            log.debug("-----------------支付订单过期---------------");
            if (OrderStatusEnum.WAITING_FOR_PAY.getValue().equals(order.getStatus())) {
                log.debug("-----------------改变状态---------------");
                order.setStatus(OrderStatusEnum.CLOSE.getValue());
                order.setRemark("订单未在指定时间内付款");
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
    }

    @Override
    public void overTimeStop(String portId, String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null != order) {
            if (OrderStatusEnum.WAITING_FOR_CONNECT.getValue().equals(order.getStatus())) {
                StationPort port = stationService.getPortById(portId);
                if (port != null) {
                    selfService.finishedOrderByStationPortId(port, "电口暂停长时间未继续充电关闭订单");
                }
            }
        }
    }

    @Override
    public void overTimeOutWill(String number) {
        //1.修改充电站状态
        Station station = stationService.getByNumber(number);
        if (null != station) {
            List<StationPort> stationPorts = stationService.getPortByStationIdAndStatus(station.getId(), new String[]{StationPortStatusEnum.WORKING.getValue(), StationPortStatusEnum.SUSPEND.getValue()});
            for (StationPort stationPort : stationPorts) {
                //订单
                selfService.finishedOrderByStationPortId(stationPort, "充电站离线关闭订单");
            }
            //
            station.setStatus(StationStatusEnum.FAULT.getValue());
            stationService.modifyStation(station);
            stationService.modifyStationPortStatusByStationId(station.getId(), StationPortStatusEnum.FAULT.getValue());
        }
    }

    /**
     * 订单 充电时间 记录
     *
     * @param order
     */
    private void addOrderRechargeTime(Order order, int type) {
        OrderRechargeTime orderRechargeTime = new OrderRechargeTime();
        orderRechargeTime.setId(UUIDUtils.createId());
        orderRechargeTime.setOrderId(order.getId());
        if (1 == type) {
            //添加
            orderRechargeTime.setStartTime(order.getStartTime());
        } else if (2 == type) {
            //修改
            orderRechargeTime.setStartTime(new Date());
        }
        orderRechargeTime.setPower(order.getPower());
        orderRechargeTime.setCreator(order.getCreator());
        orderRechargeTimeMapper.insertSelective(orderRechargeTime);
    }

    /**
     * 当前日期 获取 前15 天日期
     *
     * @param date
     * @return
     */
    private List<DateParam> listDateParam(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("\"MM\"+\"\\\\\"+\"dd\"");
        List<DateParam> dateParamList = new ArrayList<DateParam>();
        for (int i = -30; i < 1; i++) {
            Date dateNew = DateUtils.addDay(date, i);
            DateParam dateParam = new DateParam(DateUtils.getStartDate(dateNew), DateUtils.getEndDate(dateNew), simpleDateFormat.format(dateNew));
            dateParamList.add(dateParam);
        }
        return dateParamList;
    }

    /**
     * 时间 查询 只输入 一个开始 时间 获取后面 15天
     *
     * @param start
     * @return
     */
    private List<DateParam> listDateParamStart(Date start) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("\"MM\"+\"\\\\\"+\"dd\"");
        List<DateParam> dateParamList = new ArrayList<DateParam>();
        for (int i = 0; i < 30; i++) {
            Date dateNew = DateUtils.addDay(start, i);
            DateParam dateParam = new DateParam(DateUtils.getStartDate(dateNew), DateUtils.getEndDate(dateNew), simpleDateFormat.format(dateNew));
            dateParamList.add(dateParam);
        }
        return dateParamList;
    }

    /**
     * 用户 选择 的时间段来查询
     *
     * @param start
     * @param end
     * @return
     */
    private List<DateParam> listStartEndTime(Date start, Date end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("\"MM\"+\"\\\\\"+\"dd\"");
        List<DateParam> dateParamList = new ArrayList<DateParam>();
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(start);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        Date dateEnd = DateUtils.getEndDate(end);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(dateEnd);
        while (dateStart.before(calendarEnd)) {
            DateParam dateParam = new DateParam();
            dateParam.setTimeStart(dateStart.getTime());
            dateParam.setTimeEnd(DateUtils.getEndDate(dateStart.getTime()));
            dateParam.setStringDate(simpleDateFormat.format(dateStart.getTime()));
            dateParamList.add(dateParam);
            dateStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dateParamList;
    }

    /**
     * 获取 每日 对应 消耗的电量
     *
     * @param stationPortId
     * @param dateParams
     * @return
     */
    private List<StationPortPowerVo> listPortPowerVo(String stationPortId, List<DateParam> dateParams) {
        List<StationPortPowerVo> stationPortPowerVos = new ArrayList<StationPortPowerVo>();
        for (DateParam dateParam : dateParams) {
            StationPortPowerVo stationPortPowerVo = new StationPortPowerVo();
            //获取 每天 所有 order
            List<Order> orderList = orderMapper.selectByPortIdAndDate(stationPortId, dateParam.getTimeStart(), dateParam.getTimeEnd());
            double power = 0d;
            double minuteDegree = 0d;
            for (Order order : orderList) {
                //每分钟消耗功率表
                if (!StringUtils.isEmpty(order.getPower())) {
                    minuteDegree = Double.valueOf(order.getPower()) / 100 / 60;
                }
                OrderRechargeTime rechargeTime = orderRechargeTimeMapper.selectLastTime(order.getId());
                if (rechargeTime != null) {
                    if (rechargeTime.getTime() != null) {
                        power += (rechargeTime.getTime() * minuteDegree) / 60;
                    }
                }
                minuteDegree = 0d;
            }
            //日期 对应 度数
            stationPortPowerVo.setDate(dateParam.getTimeStart());
            stationPortPowerVo.setPower(String.format("%.2f", power));
            power = 0d;
            stationPortPowerVos.add(stationPortPowerVo);
        }
        return stationPortPowerVos;
    }

    /**
     * 断开 充电返回 属性
     *
     * @param passport
     * @return
     */
    private String getRemark(Passport passport) {
        String remark = "";
        if (passport.getType().equals(PassportTypeEnum.CAROWNER.getValue())) {
            remark = "车主关闭";
        }
        if (passport.getType().equals(PassportTypeEnum.OPERATOR.getValue())) {
            remark = "运营山关闭";
        }
        if (passport.getType().equals(PassportTypeEnum.ESTATE.getValue())) {
            remark = "物业关闭";
        }

        return remark;
    }

    /**
     * 微信推送
     *
     * @param messageType
     * @param remark
     * @param totalCost
     * @param station
     * @param stationPort
     * @param order
     * @param carowner
     */
    private void pushWx(String messageType, String remark, int totalCost, Station station, StationPort stationPort, Order order, Carowner carowner) {
        //获取凭证
        TokenResp tokenResp = commonService.getAccessToken(Constants.SYSTEM_ID);
        MessageTemplate messageTemplate = commonService.searchMessageTemplateType(messageType);
        AppPushMessage appPushMessage = null;
        if (messageTemplate != null && tokenResp != null) {
            PassportAccount passportAccount = passportService.getPassportAmountByPassportId(carowner.getPassportId());
            WxPushVo wxPushVo = new WxPushVo(tokenResp, messageTemplate, carowner, passportAccount);
            //时间余量充电结束通知
            if (MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue().equals(messageType)) {
                wxPushService.pushChargeTimeComlete(wxPushVo, station, stationPort, order, "本次充电已经结束。结束方式:" + remark);
                //金额充电结束通知
            } else if (MessageTemplateEnum.CHARGE_COMPLETE.getValue().equals(messageType)) {
                wxPushService.pushChargeComlete(wxPushVo, station, stationPort, order, totalCost, "本次充电已经结束。结束方式:" + remark);
            }
            //个推
            if (!StringUtils.isEmpty(carowner.getClientId())) {
                appPushMessage = new AppPushMessage("充电结束通知", "订单详情", commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId());
                appPushService.pushMessageToList(carowner.getClientId().split(",", -1), appPushService.getTransmissionTemplateDemo(appPushMessage));
            }

        }

    }
}
