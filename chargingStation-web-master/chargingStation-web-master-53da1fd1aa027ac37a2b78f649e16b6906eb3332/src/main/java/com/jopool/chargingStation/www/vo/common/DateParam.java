package com.jopool.chargingStation.www.vo.common;

import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package Name : com.jopool.chargingStation.www.vo.common
 * @Author : soupcat
 * @Creation Date : 2017/8/24 下午6:30
 */
public class DateParam {
    private Date   timeStart;
    private Date   timeEnd;
    private String stringDate;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateParam() {

    }

    public DateParam(String start, String end) throws ParseException {
        if (!StringUtils.isEmpty(start)) {
            this.timeStart = DateUtils.getStartDate(simpleDateFormat.parse(start));
        }
        if (!StringUtils.isEmpty(end)) {
            this.timeEnd = DateUtils.getEndDate(simpleDateFormat.parse(end));
        }
    }

    public DateParam(Date startTime, Date endTime, String stringDate) {
        this.timeStart = startTime;
        this.timeEnd = endTime;
        this.stringDate = stringDate;
    }
    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }
}
