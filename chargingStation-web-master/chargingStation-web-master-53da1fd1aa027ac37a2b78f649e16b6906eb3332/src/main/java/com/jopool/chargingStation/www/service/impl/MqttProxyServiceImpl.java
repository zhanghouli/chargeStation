package com.jopool.chargingStation.www.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.constants.SystemParamKeys;
import com.jopool.chargingStation.www.enums.*;
import com.jopool.chargingStation.www.mqtt.MqttService;
import com.jopool.chargingStation.www.mqtt.constants.MqttTopics;
import com.jopool.chargingStation.www.mqtt.constants.ReportTypeEnum;
import com.jopool.chargingStation.www.mqtt.messages.*;
import com.jopool.chargingStation.www.schedule.OverTimePauseJob;
import com.jopool.chargingStation.www.schedule.OverTimeWillJob;
import com.jopool.chargingStation.www.service.*;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.exceptions.JWebException;
import com.jopool.jweb.quartz.QuartzSchedulerService;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gexin on 2017/9/9.
 */
@Service
public class MqttProxyServiceImpl extends BaseServiceImpl implements MqttProxyService, SelfBeanAware<MqttProxyService> {
    private final ConcurrentMap<String, ReportMessage.St> stationIdCN2ReportMessagegStMap = new ConcurrentHashMap<String, ReportMessage.St>();
    private MqttProxyService       selfService;
    @Resource
    private QuartzSchedulerService schedulerService;
    @Resource
    private MqttService            mqttService;
    @Resource
    private StationService         stationService;
    @Resource
    private CommonService          commonService;
    @Resource
    private OrderService           orderService;
    @Resource
    private AccountService         accountService;
    @Resource
    private PassportService        passportService;

    @Override
    public void pubSetMessage(StationPort stationPort, boolean switchStatus, int openTime, boolean wait) {
        Station station = stationService.getById(stationPort.getStationId());
        if (null == station) {
            return;
        }
        SetMessage setMessage = new SetMessage();
        List<SetMessage.St> st = new ArrayList<SetMessage.St>();
        SetMessage.St st1 = new SetMessage.St();
        st1.setCn(stationPort.getSeq());
        st1.setSst(switchStatus ? 1 : 0);
        st1.setApow(stationPort.getMaxPower());
        st1.setIpow(stationPort.getMinPower());
        st1.setTck(stationPort.getTrickleTime());
        st1.setOpt(openTime);
        st.add(st1);
        setMessage.setDid(station.getNumber());
        setMessage.setT(System.currentTimeMillis() / 1000);
        setMessage.setSt(st);
        pubSetMessage(setMessage);
        String key = (station.getNumber() + "-" + stationPort.getSeq()).intern();
        //重连时候不需要同步
        if (!wait) {
            stationIdCN2ReportMessagegStMap.remove(key);
            return;
        }
        synchronized (key) {
            try {
                key.wait(Constants.MQTT_RESP_TIME_OUT * 1000);
                ReportMessage.St reportMessageSt = stationIdCN2ReportMessagegStMap.get(key);
                if (null == reportMessageSt || ReportTypeEnum.SET_SUCCESS.getValue() != reportMessageSt.getType()) {
                    Order order = orderService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.RECHARGING.getValue());
                    if (order != null && ((PayTypeEnum.WECHAT.getValue().equals(order.getPaytype()) || (PayTypeEnum.ALIPAY.getValue().equals(order.getPaytype()))))) {
                        order.setStatus(OrderStatusEnum.FINISHED.getValue());
                        order.setRemark("充电口未成功开启充电");
                        orderService.modifyOrder(order);
                        //反充到账户余额
                        Carowner carowner = passportService.getCarownerById(order.getCarownerId());
                        if (null != carowner) {
                            accountService.modifyPassportAccount(carowner.getPassportId(), order.getPayment(), PassportAccountLogTypeEnum.RETURN.getValue(), order.getCreator(), "电站故障退款");
                        }
                    } else if (order != null && PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
//                        //使用时间余量支付
//                        if (PayTypeEnum.TIMEPAY.getValue().equals(order.getPaytype())) {
//                            Carowner carowner = passportService.getCarownerById(order.getCarownerId());
//                            if (null != carowner) {
//                                passportService.stopUseTimeAccount(carowner, order);
//                            }
//                        }
//                        try {
                        throw new JWebException(Code.ERROR, "操作失败！");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    } else {
                        throw new JWebException(Code.ERROR, "操作失败！");
                    }
                }
                stationIdCN2ReportMessagegStMap.remove(key);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public void pubSetMessage(SetMessage setMessage) {
        mqttService.publish(setMessage.getDid(), MqttTopics.SET + setMessage.getDid(), setMessage);
    }

    @Override
    public void onReceivedOnlineMessage(OnlineMessage onlineMessage) {
        //1.发布参数协议
        int heartTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.HEART_TIME, "20"));
        int updateTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.UPDATE_TIME, "20"));
        int waitTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.WAIT_TIME, "20"));
        int continueTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.ORDER_CONTINUE_TIME, "10"));
        int templateMax = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.TEMPLATE_MAX, "60"));
        mqttService.publish(onlineMessage.getDid(), MqttTopics.PARAM + onlineMessage.getDid(), new ParamMessage(System.currentTimeMillis() / 1000, heartTime, updateTime, waitTime, continueTime, templateMax));
        //2.取消will定时
        schedulerService.removeSchedule(Constants.OVER_TIME_WILL_JOB + "_" + onlineMessage.getDid());
        //3.修改充电站状态
        Station station = stationService.getByNumber(onlineMessage.getDid());
        if (null != station) {
            //恢复充电口原来状态
            StationPortStatusEnum stationPortStatus;
            List<StationPort> stationPorts = stationService.getAllPortsByStationId(station.getId());
            for (StationPort stationPort : stationPorts) {
                Order order = orderService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.RECHARGING.getValue());
                if (order != null) {
                    //mqtt通知充电
                    selfService.pubSetMessage(stationPort, true, orderService.orderRestTime(order.getId()) / 60, false);
                    stationPortStatus = StationPortStatusEnum.WORKING;
                } else {
                    stationPortStatus = StationPortStatusEnum.FREE;
                }
                stationPort.setStatus(stationPortStatus.getValue());
                stationService.modifyStationPort(stationPort);
            }
            //恢复电站状态
            station.setStatus(StationStatusEnum.NORMAL.getValue());
            stationService.modifyStation(station);
        }
    }

    @Override
    public void onReceivedWillMessage(WillMessage willMessage) {
        int waitTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.WAIT_TIME, "20"));
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("number", willMessage.getDid());
        schedulerService.schedule(Constants.OVER_TIME_WILL_JOB + "_" + willMessage.getDid(), DateUtils.addSecond(new Date(), waitTime), OverTimeWillJob.class, JSONArray.toJSONString(dataMap));
    }

    @Override
    public void onReceivedStateMessage(StateMessage stateMessage) {
        Station station = stationService.getByNumber(stateMessage.getDid());
        if (null == station) {
            return;
        }
        //添加充电站监控记录
        Date date = getTimeByLong(stateMessage.getT());
        StationRealTimeListen stationRealTimeListen = new StationRealTimeListen(station, stateMessage, date);
        stationService.addStationRealTimeListen(stationRealTimeListen);
        for (StateMessage.St st : stateMessage.getSt()) {
            StationPort stationPort = stationService.getStationPortByStationIdAndSeq(station.getId(), st.getCn());
            if (null == stationPort) {
                continue;
            }
            //添加充电口监控记录
            StationPortRealTimeListen portRealTimeListen = new StationPortRealTimeListen(station, stationPort, st);
            stationService.addStationPortRealTimeListen(portRealTimeListen);
            //
            Order order = orderService.getByPortIdLastTime(stationPort.getId(), OrderStatusEnum.RECHARGING.getValue());
            if (order != null) {
                order.setPower(Double.toString(st.getPow()));
                orderService.modifyOrder(order);

            }
        }

    }

    @Override
    public void onReceivedReportMessage(ReportMessage reportMessage) {
        //
        Station station = stationService.getByNumber(reportMessage.getDid());
        if (null == station) {
            return;
        }
        for (ReportMessage.St st : reportMessage.getSt()) {
            String key = (station.getNumber() + "-" + st.getCn()).intern();
            synchronized (key) {
                if (ReportTypeEnum.SET_SUCCESS.getValue() == st.getType()) {
                    stationIdCN2ReportMessagegStMap.put(key, st);
                }
                key.notifyAll();
                //涓流情况被拔出 插头正常被拔出
                if (ReportTypeEnum.CLOSE_PULL_OUT_TRICKLE.getValue() == st.getType() || ReportTypeEnum.CLOSE_PULL_OUT_NORMAL.getValue() == st.getType()) {
                    continue;
                }
                if (ReportTypeEnum.CLOSE_PULL_OUT.getValue() == st.getType()) {
                    //异常拔出插头，设备断电，订单暂停一段时间后没有插入插头则关闭订单
                    orderService.stopRecharge(station.getId(), st.getCn(), st.getType());
                    //定时
                    int waitTime = Integer.parseInt(commonService.getConfigValueByName(SystemParamKeys.WAIT_TIME, "20"));
                    Map<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("stationId", station.getId());
                    dataMap.put("cn", st.getCn() + "");
                    schedulerService.schedule(Constants.OVER_TIME_PAUSE_JOB + "_" + station.getId() + "_" + st.getCn(), DateUtils.addSecond(new Date(), waitTime), OverTimePauseJob.class, JSONArray.toJSONString(dataMap));
                } else if (ReportTypeEnum.SET_SUCCESS.getValue() == st.getType()) {
                    //do noting
                } else {
                    orderService.stopRecharge(station.getId(), st.getCn(), st.getType());
                }
            }
        }
    }

    @Override
    public void onReceivedWarningStationMessage(WarningStationMessage warningStationMessage) {
        Date date = getTimeByLong(warningStationMessage.getT());
        Station station = stationService.getByNumber(warningStationMessage.getDid());
        if (station == null) {
            return;
        }
        Warning warning = new Warning(warningStationMessage, station, date);
        stationService.addWarning(warning);

    }

    @Override
    public void onReceivedWarningAdapterMessage(WarningAdapterMessage warningAdapterMessage) {
        //弃用
    }

    @Override
    public void setSelfBean(MqttProxyService mqttProxyService) {
        this.selfService = mqttProxyService;
    }

    /**
     * 时间戳获取时间
     *
     * @param t
     * @return
     */
    private Date getTimeByLong(long t) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long msl = t * 1000;
        Date date = new Date(msl);
        return date;
    }

}
