package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.CarLocation;
import com.jopool.chargingStation.www.dao.CarLocationMapper;
import com.jopool.chargingStation.www.service.LocationService;
import com.jopool.jweb.spring.SelfBeanAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gexin on 2017/10/24.
 */
@Service
public class LocationServiceImpl extends BaseServiceImpl implements LocationService, SelfBeanAware<LocationService> {
    private LocationService   selfServivce;
    @Resource
    private CarLocationMapper carLocationMapper;

    @Override
    public void addCarLocation(CarLocation carLocation) {
        carLocationMapper.insertSelective(carLocation);
    }

    @Override
    public void setSelfBean(LocationService object) {
        this.selfServivce = object;
    }

}
