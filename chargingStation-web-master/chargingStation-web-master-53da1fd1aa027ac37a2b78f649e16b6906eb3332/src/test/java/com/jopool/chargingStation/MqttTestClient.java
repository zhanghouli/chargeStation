package com.jopool.chargingStation;


import com.jopool.chargingStation.www.mqtt.messages.OnlineMessage;
import com.jopool.chargingStation.www.mqtt.messages.ReportMessage;
import com.jopool.chargingStation.www.mqtt.messages.SetMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gexin on 2017/9/9.
 */
public class MqttTestClient {
        private static final String            DID     = "TST00000001";
//    private static final String            DID     = "00000001";
    private static       MqttClientService service = new MqttClientService();

    /**
     * tst
     *
     * @param args
     */
    public static void main(String[] args) {
        service.init();
        //clean
//        service.publish("dc/cs/param/TST00000001", "");
        pubOnlinie();
//        pubReport();
//        pubSet();
    }

    /**
     * set
     */
    private static void pubSet() {
        SetMessage setMessage = new SetMessage();
        List<SetMessage.St> st = new ArrayList<SetMessage.St>();
        SetMessage.St st1 = new SetMessage.St();
        st1.setCn(0);
        st1.setSst(0);
        st1.setApow(210.00);
        st1.setIpow(200.00);
        st1.setTck(1);
        st1.setOpt(2);
        st.add(st1);
        setMessage.setDid(DID);
        setMessage.setT(new Date().getTime() / 1000);
        setMessage.setSt(st);
        service.publish("dc/cs/set/" + DID, setMessage);
    }

    /**
     * online
     */
    private static void pubOnlinie() {
        OnlineMessage onlineMessage = new OnlineMessage();
        onlineMessage.setDid(DID);
        service.publish("dc/cs/online/" + DID, onlineMessage);
    }

    /**
     * report
     */
    private static void pubReport() {
        ReportMessage reportMessage = new ReportMessage();
        List<ReportMessage.St> st = new ArrayList<ReportMessage.St>();
        for (int i = 0; i < 50; i++) {
            ReportMessage.St st1 = new ReportMessage.St();
            st1.setCn(i);
            st1.setSst(i % 2);
            st1.setTl(i % 2 * 60);
            st1.setType(3);
            st1.setEn(111.111);
            st.add(st1);
        }
        reportMessage.setDid(DID);
        reportMessage.setT(new Date().getTime() / 1000);
        reportMessage.setSt(st);
        service.publish("dc/cs/report/" + DID, reportMessage);
    }

}
