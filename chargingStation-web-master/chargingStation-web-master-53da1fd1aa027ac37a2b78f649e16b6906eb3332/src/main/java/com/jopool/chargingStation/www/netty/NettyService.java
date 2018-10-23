package com.jopool.chargingStation.www.netty;

import com.jopool.chargingStation.www.netty.common.TPMSConsts;
import com.jopool.chargingStation.www.netty.service.codec.Decoder4LoggingOnly;
import com.jopool.chargingStation.www.netty.service.handler.TCPServerHandler;
import com.jopool.chargingStation.www.service.NettyProxyService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by gexin on 2017/10/24.
 */
public class NettyService {
    private static final Logger log = LoggerFactory.getLogger(NettyService.class);

    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup   = null;
    private EventLoopGroup workerGroup = null;
    private int            port        = 20001;

    @Resource
    private NettyProxyService nettyProxyService;

    @PostConstruct
    public synchronized void init() {
        startServer();
    }

    @PreDestroy
    public synchronized void destory() {

    }


    public synchronized void startServer() {
        if (this.isRunning) {
            throw new IllegalStateException(this.getName() + " is already started .");
        }
        this.isRunning = true;

        new Thread(() -> {
            try {
                this.bind();
            } catch (Exception e) {
                this.log.info("TCP服务启动出错:{}", e.getMessage());
                e.printStackTrace();
            }
        }, this.getName()).start();
    }

    public synchronized void stopServer() {
        if (!this.isRunning) {
            throw new IllegalStateException(this.getName() + " is not yet started .");
        }
        this.isRunning = false;

        try {
            Future<?> future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.log.info("TCP服务已经停止...");
    }

    private void bind() throws Exception {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)//
                .channel(NioServerSocketChannel.class) //
                .childHandler(new ChannelInitializer<SocketChannel>() { //
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //超过15分钟未收到客户端消息则自动断开客户端连接
                        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(TPMSConsts.tcp_client_idle_minutes, 0, 0, TimeUnit.MINUTES));
                        ch.pipeline().addLast(new Decoder4LoggingOnly());
                        // 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("*".getBytes()), Unpooled.copiedBuffer("$".getBytes()), Unpooled.copiedBuffer("#*".getBytes())));
                        ch.pipeline().addLast(new TCPServerHandler(nettyProxyService));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) //
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        log.info("TCP服务启动完毕,port={}", this.port);
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        channelFuture.channel().closeFuture().sync();
    }

    private String getName() {
        return "TCP-Server";
    }

    public static void main(String[] args) throws Exception {
        NettyService server = new NettyService();
        server.startServer();
        // Thread.sleep(3000);
        // server.stopServer();
    }
}
