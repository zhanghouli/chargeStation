package com.jopool.chargingStation.www.service.task;

import com.jopool.chargingStation.www.base.entity.CarLocation;
import com.jopool.chargingStation.www.base.entity.Carowner;
import com.jopool.chargingStation.www.enums.FenceStatusEnum;
import com.jopool.chargingStation.www.netty.vo.req.LocationMsg;
import com.jopool.chargingStation.www.service.FenceService;
import com.jopool.chargingStation.www.service.LocationService;
import com.jopool.chargingStation.www.service.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.awt.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gexin on 15/7/21.
 */
@Service
public class BoundsConsumerTask {
    public static        BlockingDeque<LocationMsg> trackQueue = new LinkedBlockingDeque<LocationMsg>();
    private final static Logger                     logger     = LoggerFactory.getLogger(BoundsConsumerTask.class);
    @Resource
    private FenceService    fenceService;
    @Resource
    private LocationService locationService;
    @Resource
    private PassportService passportService;

    @PostConstruct
    public void init() {
        new Thread(new BoundsConsumer()).start();
    }

    @PreDestroy
    public void destory() {

    }

    class BoundsConsumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    final LocationMsg msg = trackQueue.take();
                    if (msg == null) {
                        continue;
                    }
                    //新增日志
                    CarLocation carLocation = new CarLocation(msg);
                    Carowner carowner = passportService.getCarownerByDeviceNumber(msg.getMsgHeader().getDeviceNumber());
                    if (null == carowner) {
                        continue;
                    }
                    carLocation.setCarownerId(carowner.getId());
                    locationService.addCarLocation(carLocation);
                    //判断是否在区域内
                    List<Polygon> polygons = fenceService.getPolygonsByCarownerId(carowner.getId());
                    if (null == polygons || polygons.size() <= 0) {
                        continue;
                    }
                    FenceStatusEnum fenceStatus = FenceStatusEnum.OUT;
                    for (Polygon polygon : polygons) {
                        if (polygon.contains(carLocation.getBdLng(), carLocation.getBdLat())) {
                            fenceStatus = FenceStatusEnum.IN;
                            break;
                        }
                    }
                    if (FenceStatusEnum.OUT == fenceStatus) {
                        //TODO 0-6点需要通知相关部门
                        logger.debug("[电子围栏]超出围栏，{}", carowner.getId());
                    }
                    carowner.setFenceStatus(fenceStatus.getValue());
                    carowner.setBdLng(carLocation.getBdLng());
                    carowner.setBdLat(carLocation.getBdLat());
                    passportService.modifyCarOwner(carowner);

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
