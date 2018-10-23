package com.jopool.chargingStation.www.mqtt.messages;

import java.util.List;

/**
 * Created by gexin on 2017/9/9.
 */
public class WarningAdapterMessage {
    private String                         did;
    private long                           t;
    private List<WarningAdapterMessage.St> st;

    public static class St {
        private int    cn;
        private int    warning;
        private double cur;
        private double pow;

        public int getCn() {
            return cn;
        }

        public void setCn(int cn) {
            this.cn = cn;
        }

        public int getWarning() {
            return warning;
        }

        public void setWarning(int warning) {
            this.warning = warning;
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
