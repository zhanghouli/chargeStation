package com.jopool.chargingStation.www.vo;

/**
 * Created by synn on 2017/11/10.
 */
public class AccountVo {
    private String timeStartStr;

    private String timeEndStr;

    private String day="month";

    private String keyword;


    public String getTimeStartStr() {
        return timeStartStr;
    }

    public void setTimeStartStr(String timeStartStr) {
        this.timeStartStr = timeStartStr;
    }

    public String getTimeEndStr() {
        return timeEndStr;
    }

    public void setTimeEndStr(String timeEndStr) {
        this.timeEndStr = timeEndStr;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
