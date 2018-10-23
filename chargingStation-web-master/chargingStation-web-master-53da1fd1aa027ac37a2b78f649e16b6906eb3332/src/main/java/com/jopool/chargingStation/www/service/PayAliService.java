package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.AliConfig;
import com.jopool.chargingStation.www.base.entity.AlipayResponse;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.mybatis.page.Pagination;

import java.util.List;

/**
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017/11/28 下午5:06
 */
public interface PayAliService {
    /**
     * 支付回调
     *
     * @param alipayResponse
     * @return
     */
    boolean alipay(AlipayResponse alipayResponse);

    /**
     * searh
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<AliConfig> search(SearchBaseVo searchBaseVo, Pagination page);

    /**
     * 获取配置 by id
     *
     * @param payAliId
     * @return
     */
    AliConfig getById(String payAliId);

    /**
     * 修改
     *
     * @param payAliReq
     */
    void modify(AliConfig payAliReq);

    /**
     * 添加
     *
     * @param payAliReq
     */
    void add(AliConfig payAliReq);

    /**
     * remove
     *
     * @param payAliId
     */
    void removeById(String payAliId);

    /**
     * 获取最新配置
     *
     * @return
     */
    AliConfig getByCreationTimeDesc();
}
