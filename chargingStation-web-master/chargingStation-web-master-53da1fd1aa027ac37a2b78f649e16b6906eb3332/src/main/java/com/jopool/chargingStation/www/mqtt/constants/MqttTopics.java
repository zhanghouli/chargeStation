package com.jopool.chargingStation.www.mqtt.constants;

/**
 * mqtt 主题
 * Created by gexin on 2017/9/9.
 */
public class MqttTopics {
    //订阅主题
    public static final String[] TOPICS     = {"dc/cs/online/+", "dc/cs/will/+", "dc/cs/state/+", "dc/cs/report/+", "dc/cs/warning/#"};
    //订阅主题对应qos
    //QoS=0，最多一次，有可能重复或丢失
    //QoS=1：至少一次，有可能重复
    //QoS=2：只有一次，确保消息只到达一次
    public static final int[]    QOS_VALUES = {2, 2, 2, 2, 2};
    //发布主题
    public static final String   PARAM      = "dc/cs/param/";
    public static final String   SET      = "dc/cs/set/";
}
