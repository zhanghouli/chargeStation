package com.jopool.chargingStation.www.vo.common;

import com.jopool.jweb.utils.StringUtils;

/**
 * Created by synn on 2017/8/29.
 */
public class SearchStationVo extends SearchBaseVo {
    private String status;
    private String estateId;
    private String operatorId;
    private String stationId;
    private String searchProvince;
    private String searchCity;
    private String searchTown;
    private String areaID;
    private String stationType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstateId() {
        if (StringUtils.isEmpty(this.estateId)) {
            this.estateId = null;
        }
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getSearchProvince() {
        return searchProvince;
    }

    public void setSearchProvince(String searchProvince) {
        this.searchProvince = searchProvince;
    }

    public String getSearchCity() {
        return searchCity;
    }

    public void setSearchCity(String searchCity) {
        this.searchCity = searchCity;
    }

    public String getSearchTown() {
        return searchTown;
    }

    public void setSearchTown(String searchTown) {
        this.searchTown = searchTown;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getOperatorId() {
        if (StringUtils.isEmpty(this.operatorId)) {
            this.operatorId = null;
        }
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationType() {
        if (StringUtils.isEmpty(this.stationType)) {
            this.stationType = null;
        }
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }
}
