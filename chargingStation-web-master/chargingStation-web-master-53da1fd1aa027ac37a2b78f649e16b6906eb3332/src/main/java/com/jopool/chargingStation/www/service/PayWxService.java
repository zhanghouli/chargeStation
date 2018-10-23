package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017/8/24 下午6:27
 */
public interface PayWxService {

    /**
     * search
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<WxConfig> search(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * get by id
     *
     * @param id
     * @return
     */
    WxConfig getById(String id);

    /**
     * add
     *
     * @param payWx
     * @return
     */
    int add(WxConfig payWx);

    /**
     * modify
     *
     * @param payWx
     */
    Result modify(WxConfig payWx);

    /**
     * remove by id
     *
     * @param id
     */
    void removeById(String id);

    /**
     * get by platform
     *
     * @param platform
     * @return
     */
    WxConfig getByShopIdPlatform(String platformId, String platform);

}
