package com.jopool.chargingStation.www.vo;

import java.util.Map;

/**
 * Created by synn on 2017/9/1.
 */
public class ConsumePackageIdVo {
    private Map<String, String> maps;
    private String              operatorId;
    private String              area;
    //运行商分配比例 default 10%
    private String              operatorDivided;
    //物业分配比例 default 30%
    private String              estateDivided;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Map<String, String> getMaps() {

        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorDivided() {
        return operatorDivided;
    }

    public void setOperatorDivided(String operatorDivided) {
        this.operatorDivided = operatorDivided;
    }

    public String getEstateDivided() {
        return estateDivided;
    }

    public void setEstateDivided(String estateDivided) {
        this.estateDivided = estateDivided;
    }
}
