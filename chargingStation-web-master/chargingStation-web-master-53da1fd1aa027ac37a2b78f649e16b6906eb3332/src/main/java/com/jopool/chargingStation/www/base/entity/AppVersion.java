package com.jopool.chargingStation.www.base.entity;

import java.io.Serializable;
import java.util.Date;

public class AppVersion implements Serializable {
    private String id;

    private String appId;

    private String os;

    private String lastVersion;

    private String url;

    private Boolean isForceUpdate;

    private String description;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}