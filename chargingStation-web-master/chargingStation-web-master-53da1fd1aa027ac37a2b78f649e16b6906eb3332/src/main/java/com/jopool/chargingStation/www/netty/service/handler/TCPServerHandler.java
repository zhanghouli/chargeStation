package com.jopool.chargingStation.www.netty.service.handler;

import com.alibaba.fastjson.JSON;
import com.jopool.chargingStation.www.netty.common.TPMSConsts;
import com.jopool.chargingStation.www.netty.server.SessionManager;
import com.jopool.chargingStation.www.netty.service.codec.MsgDecoder;
import com.jopool.chargingStation.www.netty.vo.PackageData;
import com.jopool.chargingStation.www.netty.vo.Session;
import com.jopool.chargingStation.www.netty.vo.req.HeartbeatMsg;
import com.jopool.chargingStation.www.netty.vo.req.LocationMsg;
import com.jopool.chargingStation.www.service.NettyProxyService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SessionManager    sessionManager;
    private final MsgDecoder        decoder;
    private final NettyProxyService nettyProxyService;

    public TCPServerHandler(NettyProxyService nettyProxyService) {
        this.sessionManager = SessionManager.getInstance();
        this.decoder = new MsgDecoder();
        this.nettyProxyService = nettyProxyService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
        try {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.readableBytes() <= 0) {
                return;
            }

            byte[] bs = new byte[buf.readableBytes()];
            buf.readBytes(bs);
            // 字节数据转换实体类
            PackageData pkg = this.decoder.bytes2PackageData(bs);
            // 引用channel,以便回送数据给硬件
            pkg.setChannel(ctx.channel());
            this.processPackageData(pkg);
        } finally {
            release(msg);
        }
    }

    /**
     * 处理业务逻辑
     *
     * @param packageData
     */
    private void processPackageData(PackageData packageData) {
        // 1. 终端心跳-消息体为空 ==> 平台通用应答
        if (TPMSConsts.MSG_TYPE_TERMINAL_HEART_BEAT.equals(packageData.getMsgHeader().getMsgType())) {
            logger.info("[终端心跳],header={}", packageData);
            try {
                HeartbeatMsg heartbeatMsg = new HeartbeatMsg(packageData);
                logger.debug("[终端心跳]body={}", JSON.toJSONString(heartbeatMsg, true));
                this.nettyProxyService.processHeartbeatMsg(heartbeatMsg);
            } catch (Exception e) {
                logger.error("[终端心跳]处理错误,msg={},err={}", packageData, e.getMessage());
                e.printStackTrace();
            }
        }
        // 3. 位置信息汇报 ==> 平台通用应答
        else if (TPMSConsts.MSG_TYPE_TERMINAL_LOCATION_INFO_UPLOAD.equals(packageData.getMsgHeader().getMsgType())) {
            logger.info("[位置信息]header={}", packageData);
            try {
                LocationMsg locationMsg = new LocationMsg(packageData);
                logger.debug("[位置信息]body={}", JSON.toJSONString(locationMsg, true));
                this.nettyProxyService.processLocationMsg(locationMsg);
            } catch (Exception e) {
                logger.error("[位置信息]处理错误,msg={},err={}", packageData, e.getMessage());
                e.printStackTrace();
            }
        }
        // 其他情况
        else {
            logger.error("[未知消息类型],msg={}", packageData);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        logger.error("发生异常:{}", cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        logger.debug("终端连接:{}", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final String sessionId = ctx.channel().id().asLongText();
        Session session = sessionManager.findBySessionId(sessionId);
        this.sessionManager.removeBySessionId(sessionId);
        this.nettyProxyService.processInactive(session.getDeviceNumber());
        logger.debug("终端断开连接:{}", session);
        ctx.channel().close();
        // ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                logger.error("服务器主动断开连接:{}", session);
                ctx.close();
            }
        }
    }

    private void release(Object msg) {
        try {
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}