package com.jopool.chargingStation.www.mqtt.messages;

/**
 * Created by gexin on 2017/9/9.
 */
public class WarningStationMessage {
    private String did;
    private long   t;
    private int    warning;
    private double temp;

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

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
