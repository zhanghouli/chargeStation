package com.jopool.chargingStation.www.mqtt.messages;

import java.util.List;

/**
 * Created by gexin on 2017/9/9.
 */
public class StateMessage {
    private String                did;
    private String                v;//版本
    private long                  t;//时间
    private double                temp;//充电站温度，数字，单位摄氏度
    private double                vol;//电源电压，数字，单位V
    private double                en;//电能量，数字，单位kwh
    private List<StateMessage.St> st;

    public static class St {
        private int    cn;//插座编号，数字，1开始
        private double cur;//插座电流：数字，单位A
        private double pow;//有功功率，数字，单位W
        private int    sst;//插座通断状态，数字，0-断开，（非0-接通）1-待插头，2-充电中，3-涓流中
        private double en;//电能量，数字，单位kwh
        private int    tl;//插座剩余时间，数字，单位分钟

        public int getCn() {
            return cn;
        }

        public void setCn(int cn) {
            this.cn = cn;
        }

        public double getCur() {
            return cur;
        }

        public void setCur(double cur) {
            this.cur = cur;
        }

        public double getPow() {
            return pow;
        }

        public void setPow(double pow) {
            this.pow = pow;
        }

        public int getSst() {
            return sst;
        }

        public void setSst(int sst) {
            this.sst = sst;
        }

        public double getEn() {
            return en;
        }

        public void setEn(double en) {
            this.en = en;
        }

        public int getTl() {
            return tl;
        }

        public void setTl(int tl) {
            this.tl = tl;
        }
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getEn() {
        return en;
    }

    public void setEn(double en) {
        this.en = en;
    }

    public List<St> getSt() {
        return st;
    }

    public void setSt(List<St> st) {
        this.st = st;
    }
}
