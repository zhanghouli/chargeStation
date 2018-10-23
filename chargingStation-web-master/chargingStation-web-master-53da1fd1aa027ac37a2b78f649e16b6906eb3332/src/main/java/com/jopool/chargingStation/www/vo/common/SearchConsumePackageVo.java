package com.jopool.chargingStation.www.vo.common;

/**
 * Created by synn on 2017/8/28.
 */
public class SearchConsumePackageVo extends SearchBaseVo {
    private String status;

    private String operatorId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
