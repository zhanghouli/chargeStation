package com.jopool.chargingStation.www.mqtt.constants;

/**
 * Created by synn on 2017/8/29.
 */
public enum ReportTypeEnum {
    SET_FAIL(-3, "设定开关状态失败"),
    CLOSE_OTHER(-1, "其它异常关闭"),
    CLOSE_NORMAL(0, "正常充电结束关闭"),
    CLOSE_MIN_POWER(1, "小于下限功率涓流结束关闭"),
    CLOSE_MAX_POWER(2, "大于上限功率"),
    SET_SUCCESS(3, "设定开关状态成功"),
    CLOSE_WAIT_TIMEOUT(4, "通电后一定时间没有插入插头自动关闭"),
    CLOSE_PULL_OUT(5, "插头被拔出"),
    CLOSE_PULL_OUT_TRICKLE(6, "涓流情况被拔出"),
    CLOSE_PULL_OUT_NORMAL(7, "插头正常被拔出");

    private int    value;
    private String name;

    ReportTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getRoportTypeName(int value) {
        ReportTypeEnum reportTypeEnum = CLOSE_NORMAL;
        for (ReportTypeEnum reportTypeEnum1 : ReportTypeEnum.values()) {
            if (reportTypeEnum1.getValue() == value) {
                reportTypeEnum = reportTypeEnum1;
                break;
            }
        }
        return reportTypeEnum.getName();
    }
}
