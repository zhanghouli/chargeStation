package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.utils.LBSUtils;
import com.jopool.jweb.cache.Cache;
import com.jopool.jweb.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.popular.api.BaseAPI;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 下午1:43
 */
public class BaseServiceImpl  extends BaseAPI{
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    protected Cache cacheBean;

    public String getDistanceByLngLat(int lngE5, int latE5, int lngE52, int latE52) {
        String distan = "";
        double distance = 0.0;
        if (lngE5 == 0 || latE5 == 0 || lngE52 == 0 || latE52 == 0) {
            distan = "-";
            return distan;
        } else {
            distance = LBSUtils.distance(lngE5, latE5, lngE52, latE52);
            BigDecimal bg = new BigDecimal(distance);
            distance = bg.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        if (MathUtils.div(distance, 1000) > 1) {
            DecimalFormat df = new DecimalFormat("0.0");
            distan = df.format(MathUtils.div(distance, 1000)) + "km";
        } else {
            distan = distance + "m";
        }
        return distan;
    }
}
