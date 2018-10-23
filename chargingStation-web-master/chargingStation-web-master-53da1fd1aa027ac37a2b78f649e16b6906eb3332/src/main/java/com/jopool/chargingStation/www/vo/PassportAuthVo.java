package com.jopool.chargingStation.www.vo;

import java.io.Serializable;

/**
 * Created by synn on 2017/8/24.
 */
public class PassportAuthVo implements Serializable{
    private String id;

    private String name;

    private String url;

    private boolean hasAuth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasAuth() {
        return hasAuth;
    }

    public void setHasAuth(boolean hasAuth) {
        this.hasAuth = hasAuth;
    }
}
