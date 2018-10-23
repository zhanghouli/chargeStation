package com.jopool.chargingStation.www.service.task;

import com.jopool.chargingStation.www.base.entity.MqttMessageLog;
import com.jopool.chargingStation.www.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gexin on 15/7/21.
 */
public class MqttMessageLogTask {
    public static        BlockingDeque<MqttMessageLog> msgQueue = new LinkedBlockingDeque<MqttMessageLog>();
    private final static Logger                        logger     = LoggerFactory.getLogger(MqttMessageLogTask.class);
    @Resource
    private LogService logService;

    @PostConstruct
    public void init() {
        new Thread(new MqttMessageLogConsumer()).start();
    }

    @PreDestroy
    public void destory() {

    }

    class MqttMessageLogConsumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    final MqttMessageLog msg = msgQueue.take();
                    if (msg == null) {
                        continue;
                    }
                    logService.addMqttLog(msg);

                } catch (Exception ex) {
                    logger.error("mqtt log error:{}",ex.getMessage());
                }
            }
        }

    }
}
