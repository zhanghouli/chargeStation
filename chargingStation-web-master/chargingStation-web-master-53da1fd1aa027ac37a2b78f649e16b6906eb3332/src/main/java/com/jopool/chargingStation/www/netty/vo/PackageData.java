package com.jopool.chargingStation.www.netty.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import io.netty.channel.Channel;

public class PackageData {

    //消息头
    protected MsgHeader msgHeader;
    // 消息体字节数组
    @JSONField(serialize = false)
    protected byte[]    msgBodyBytes;
    @JSONField(serialize = false)
    protected Channel   channel;

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public byte[] getMsgBodyBytes() {
        return msgBodyBytes;
    }

    public void setMsgBodyBytes(byte[] msgBodyBytes) {
        this.msgBodyBytes = msgBodyBytes;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static class MsgHeader {
        // 消息类型
        protected String msgType;
        //设备序列号id
        protected String deviceNumber;

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

    }


}
