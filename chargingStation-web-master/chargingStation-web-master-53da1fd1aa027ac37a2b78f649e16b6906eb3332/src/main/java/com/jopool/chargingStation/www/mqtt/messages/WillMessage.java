package com.jopool.chargingStation.www.mqtt.messages;

/**
 * Created by gexin on 2017/9/9.
 */
public class WillMessage {
    private String did;
    private long   t;

    public WillMessage() {
    }

    public WillMessage(String did, long t) {
        this.did = did;
        this.t = t;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }
}
