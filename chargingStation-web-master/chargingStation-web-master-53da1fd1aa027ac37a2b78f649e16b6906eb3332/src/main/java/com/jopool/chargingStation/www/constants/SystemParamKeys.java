package com.jopool.chargingStation.www.constants;

/**
 * @Package Name : com.jopool.chargingStation.www.constants
 * @Author : soupcat
 * @Creation Date : 2017/9/8 下午4:12
 */
public class SystemParamKeys {

    /**
     * 下单后未付款,自动取消订单,单位:分钟
     */
    public static final String AUTO_CANCEL_TIME    = "pay.order.pay.wait.timeout";
    public static final String ORDER_CONTINUE_TIME = "order.continue.charging.time";
    public static final String HEART_TIME          = "heart.time.value";
    public static final String UPDATE_TIME         = "update.time.value";
    public static final String WAIT_TIME           = "wait.time.value";
    public static final String STOP_WAIT_TIME      = "stop.wait.time.value";
    public static final String TEMPLATE_MAX        = "template.max";
    public static final String MAX_POWER           = "max.power.default.value";
    public static final String MIN_POWER           = "min.power.default.value";
    public static final String TRICKLE_TIME        = "trickle.time.default.value";
    public static final String IS_AUTO             = "is.auto.stop.default.value";
    public static final String IS_LARGE            = "is.large.power.default.value";
    public static final String OPERATOR_DIVIDED    = "operator.divided.proportion";
    public static final String ESTATE_DIVIDED      = "estate.divided.proportion";
    public static final String WX_TUI              = "wx.tui";
    //历史数据采样最多点数
    public static final String MAX_DATA_COUNT      = "max.data.count";
}
