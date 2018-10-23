package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.RechargePackage;
import com.jopool.chargingStation.www.base.entity.RechargePackageSnapshot;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午6:55
 */
public interface RechargePackageService {
    /**
     * 获取充值套餐
     *
     * @param rechargePackageId
     * @return
     */
    RechargePackage getById(String rechargePackageId);

    /**
     * 添加 充值 套餐
     *
     * @param rechargePackage
     */
    void add(RechargePackage rechargePackage);

    /**
     * 状态 修改
     *
     * @param rechargePackageId
     * @return
     */
    Result modifyRechargePackageIsEnabled(String rechargePackageId);

    /**
     * 修改 充值 套餐
     *
     * @param rechargePackage
     * @return
     */
    Result modifyRechargePackage(RechargePackage rechargePackage);

    /**
     * 删除 套餐
     *
     * @param rechargePackageId
     * @return
     */
    Result removeRechargePackageId(String rechargePackageId);

    /**
     * 套餐 分页 查询
     *
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    List<RechargePackage> searchRechargePackageVo(SearchRechargePackageVo searchRechargePackageVo, RowBounds page);

    /**
     * add
     *
     * @param rechargePackage
     * @return
     */
    RechargePackageSnapshot addRechargePackageSnapshot(RechargePackage rechargePackage,int rechargeAmount);
}
