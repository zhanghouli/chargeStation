package com.jopool.chargingStation.www.mqtt.messages;

/**
 * Created by gexin on 2017/9/9.
 */
public class ParamMessage {
    private long t;//当前服务器时间，数字，世纪秒
    private int  ht;//心跳时间间隔，数字，单位秒
    private int  ut;//自动上报时间间隔，数字，单位秒
    private int  wt;//收到开命令后多久时间没有插入插头自动断开，数字，单位秒
    private int  ct;//断电后多久可以续充（单位：分钟)
    private int  temp;//温度上限，温度超过后报警

    public ParamMessage() {
    }

    public ParamMessage(long t, int ht, int ut, int wt, int ct, int temp) {
        this.t = t;
        this.ht = ht;
        this.ut = ut;
        this.wt = wt;
        this.ct = ct;
        this.temp = temp;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public int getHt() {
        return ht;
    }

    public void setHt(int ht) {
        this.ht = ht;
    }

    public int getUt() {
        return ut;
    }

    public void setUt(int ut) {
        this.ut = ut;
    }

    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
