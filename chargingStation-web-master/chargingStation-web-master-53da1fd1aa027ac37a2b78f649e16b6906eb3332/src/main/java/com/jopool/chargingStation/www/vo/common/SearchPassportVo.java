package com.jopool.chargingStation.www.vo.common;

import java.util.Date;

/**
 * Created by synn on 2017/8/28.
 */
public class SearchPassportVo extends SearchBaseVo {

    private int isEnabled=-1;

    private String status;

    private Date timeStartStr;

    private Date timeEndStr;

    private String operatorId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimeStartStr() {
        return timeStartStr;
    }

    public void setTimeStartStr(Date timeStartStr) {
        this.timeStartStr = timeStartStr;
    }

    public Date getTimeEndStr() {
        return timeEndStr;
    }

    public void setTimeEndStr(Date timeEndStr) {
        this.timeEndStr = timeEndStr;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }


    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
