package com.jopool.chargingStation.www.mqtt.messages;

import java.util.List;

/**
 * Created by gexin on 2017/9/9.
 */
public class ReportMessage {
    private String   did;
    private long     t;
    private List<St> st;

    public static class St {
        private int    cn;
        private int    sst;
        private int    tl;
        private int    type;
        private double en;

        public int getCn() {
            return cn;
        }

        public void setCn(int cn) {
            this.cn = cn;
        }

        public int getSst() {
            return sst;
        }

        public void setSst(int sst) {
            this.sst = sst;
        }

        public int getTl() {
            return tl;
        }

        public void setTl(int tl) {
            this.tl = tl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getEn() {
            return en;
        }

        public void setEn(double en) {
            this.en = en;
        }
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

    public List<St> getSt() {
        return st;
    }

    public void setSt(List<St> st) {
        this.st = st;
    }
}
