package com.jopool.chargingStation.www.request;

/**
 * Created by gexin on 15/7/7.
 */
public class CheckVersionReq {
    private String version;//运行的app版本
    private String os;//终端类型Android/iOS
    private String appId;// 应用标识符号，字符串，一般APP端可以传包名

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
