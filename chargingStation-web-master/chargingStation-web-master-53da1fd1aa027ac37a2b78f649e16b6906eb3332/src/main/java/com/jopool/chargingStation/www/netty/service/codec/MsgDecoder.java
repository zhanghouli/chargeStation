package com.jopool.chargingStation.www.netty.service.codec;

import com.jopool.chargingStation.www.netty.common.TPMSConsts;
import com.jopool.chargingStation.www.netty.util.BytesUtils;
import com.jopool.chargingStation.www.netty.vo.PackageData;
import com.jopool.chargingStation.www.netty.vo.PackageData.MsgHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgDecoder {

    private static final Logger log = LoggerFactory.getLogger(MsgDecoder.class);

    public MsgDecoder() {
    }

    public PackageData bytes2PackageData(byte[] data) {
        PackageData ret = new PackageData();

        // 1.消息头
        MsgHeader msgHeader = this.parseMsgHeaderFromBytes(data);
        ret.setMsgHeader(msgHeader);
        // 2. 消息体
        int bodyLength = 0;
        if (isHeartbeat(data)) {
            bodyLength = new String(data).getBytes().length - 3;
        } else {
            bodyLength = 44;
        }
        byte[] tmp = new byte[bodyLength];
        System.arraycopy(data, 0, tmp, 0, tmp.length);
        ret.setMsgBodyBytes(tmp);
        return ret;
    }

    /**
     * 消息头
     *
     * @param data
     * @return
     */
    private MsgHeader parseMsgHeaderFromBytes(byte[] data) {
        MsgHeader msgHeader = new MsgHeader();
        if (isHeartbeat(data)) {//心跳消息
            msgHeader.setMsgType(TPMSConsts.MSG_TYPE_TERMINAL_HEART_BEAT);
            String datas[] = new String(data).split(",");
            if (datas.length >= 2) {
                msgHeader.setDeviceNumber(datas[1]);
            }
        } else {//正常消息
            msgHeader.setMsgType(TPMSConsts.MSG_TYPE_TERMINAL_LOCATION_INFO_UPLOAD);
            msgHeader.setDeviceNumber(BytesUtils.parseHexStringFromBytes(data, 0, 5));
        }
        return msgHeader;
    }

    /**
     * 是否心跳消息
     *
     * @param data
     * @return
     */
    public static boolean isHeartbeat(byte[] data) {
        boolean r = false;
        if (data.length != 44) {//心跳消息
            r = true;
        }
        return r;
    }

}
