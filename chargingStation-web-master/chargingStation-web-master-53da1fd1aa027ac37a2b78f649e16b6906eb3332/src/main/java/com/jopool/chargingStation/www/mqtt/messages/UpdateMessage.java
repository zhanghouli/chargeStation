package com.jopool.chargingStation.www.mqtt.messages;

/**
 * Created by gexin on 2017/9/9.
 */
public class UpdateMessage {
    private long   t;
    private String v;
    private String url;

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
