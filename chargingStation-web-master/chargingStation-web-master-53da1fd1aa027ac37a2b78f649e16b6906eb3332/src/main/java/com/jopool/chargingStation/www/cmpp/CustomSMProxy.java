package com.jopool.chargingStation.www.cmpp;

import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SMProxy30;

import java.util.Map;

/**
 * Created by gexin on 2017/12/21.
 */
public class CustomSMProxy extends SMProxy30 {
    private CmppService.OnDeliverListener listener;

    public CustomSMProxy(Map args) {
        super(args);
    }

    public CustomSMProxy(Args args) {
        super(args);
    }

    public CustomSMProxy(Args args, CmppService.OnDeliverListener listener) {
        super(args);
        this.listener = listener;
    }

    @Override
    public CMPPMessage onDeliver(CMPP30DeliverMessage msg) {
        listener.onDeliver(msg);
        return super.onDeliver(msg);
    }
}
