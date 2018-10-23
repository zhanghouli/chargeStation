package com.jopool.chargingStation.www.mqtt.messages;

import java.util.List;

/**
 * Created by gexin on 2017/9/9.
 */
public class SetMessage {
    private String              did;
    private long                t;
    private List<SetMessage.St> st;

    public static class St {
        private int    cn;
        private int    sst;
        private double apow;
        private double ipow;
        private int    tck;
        private int    opt;

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

        public double getApow() {
            return apow;
        }

        public void setApow(double apow) {
            this.apow = apow;
        }

        public double getIpow() {
            return ipow;
        }

        public void setIpow(double ipow) {
            this.ipow = ipow;
        }

        public int getTck() {
            return tck;
        }

        public void setTck(int tck) {
            this.tck = tck;
        }

        public int getOpt() {
            return opt;
        }

        public void setOpt(int opt) {
            this.opt = opt;
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
