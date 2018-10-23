package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.CarLocation;

/**
 * Created by gexin on 2017/10/24.
 */
public interface LocationService {
    /**
     * 新增车辆定位日志
     *
     * @param carLocation
     */
    void addCarLocation(CarLocation carLocation);
}
