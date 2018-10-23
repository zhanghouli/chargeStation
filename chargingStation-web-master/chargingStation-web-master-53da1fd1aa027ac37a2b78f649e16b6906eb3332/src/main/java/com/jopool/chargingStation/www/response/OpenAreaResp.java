package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.OpenArea;

import java.util.Date;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年08月30日 下午3:31
 */
public class OpenAreaResp {
    private String id;
    private String code;
    private String name;
    private String location;
    private Date   creationTime;

    public OpenAreaResp(OpenArea openArea) {
        this.id = openArea.getId();
        this.code = openArea.getAreaId();
        this.creationTime = openArea.getCreationTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
