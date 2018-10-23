package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.ChargeCostPackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;
import com.jopool.chargingStation.www.response.ConsumePackageResp;
import com.jopool.chargingStation.www.vo.ConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.ConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by synn on 2017/8/28.
 */
public interface ConsumePackageService {
    /**
     * 添加
     *
     * @param consumePackage
     */
    void addConsumePackage(ConsumePackage consumePackage);

    /**
     * id 查找
     *
     * @param consumePackageId
     * @return
     */
    ConsumePackage searchConsumePackageById(String consumePackageId);

    /**
     * 修改
     *
     * @param consumePackage
     * @return
     */
    Result modifyConsumePackage(ConsumePackage consumePackage);

    /**
     * 状态  修改
     *
     * @param consumePackageId
     * @return
     */
    Result modifyStatus(String consumePackageId);

    /**
     * id  删除
     *
     * @param consumePackageId
     * @return
     */
    Result removeConsumePackageById(String consumePackageId);

    /**
     * 分页 查询
     *
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    List<ConsumePackageVo> searchConsumePackageVo(SearchConsumePackageVo searchConsumePackageVo, RowBounds page);

    /**
     * 添加 套餐 详情
     *
     * @param consumePackageItem
     */
    void addConsumePackageItem(ConsumePackageItem consumePackageItem);

    /**
     * 单挑 记录 查询
     *
     * @param consumePackageItemId
     * @return
     */
    ConsumePackageItem getById(String consumePackageItemId);

    /**
     * 修改 套餐 详情
     *
     * @param consumePackageItem
     * @return
     */
    Result modifyConsumePackageItem(ConsumePackageItem consumePackageItem);

    /**
     * 修改 套餐 状态
     *
     * @param consumePackageItemId
     * @return
     */
    Result modifyStatusConsumePackageItem(String consumePackageItemId);

    /**
     * 按照  id 删除
     *
     * @param consumePackageItemId
     * @return
     */
    Result removeConsumePackageItemId(String consumePackageItemId);

    /**
     * @param searchConsumePackageItemVo
     * @param page
     * @return
     */
    List<ConsumePackageItemVo> searchConsumePackageItemVo(String consumePackageId, SearchConsumePackageItemVo searchConsumePackageItemVo, RowBounds page);

    /**
     * 运营商 id 查找 套餐
     *
     * @param operatorId
     * @return
     */
    List<ConsumePackage> searchOperatorId(String operatorId);

    /**
     * 获取套餐具体信息
     *
     * @param consumePackageId
     * @return
     */
    List<ConsumePackageItem> getItemsByConsumePackageId(String consumePackageId);

    /**
     * 获取套餐详情
     *
     * @param consumePackageId
     * @return
     */
    ConsumePackageResp getConsumePackageDetail(String consumePackageId);

    /**
     * 套餐列表
     *
     * @return
     */
    List<ConsumePackage> getConsumePackageList(String operatorId);


    /**
     * @param operatorId
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    List<ConsumePackage> listOperatorIdByConsumePackage(String operatorId, SearchConsumePackageVo searchConsumePackageVo, RowBounds page);

    /**
     * 按量充值套餐
     *
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    List<ChargeCostPackage> searchChargeCostPackage(SearchRechargePackageVo searchRechargePackageVo, RowBounds page);

    /**
     * 获取 ChargeCostPackage 按量充值规则 by id
     *
     * @param id
     * @return
     */
    ChargeCostPackage getChargeCostPackageById(String id);

    /**
     * 修改 按量充值规则
     *
     * @param chargeCostPackage
     */
    void modifyChargeCostPackage(ChargeCostPackage chargeCostPackage);

    /**
     * add ChargeCostPackage
     *
     * @param chargeCostPackage
     */
    void addChargeCostPackage(ChargeCostPackage chargeCostPackage);

    /**
     * 删除 按量充值 配置
     *
     * @param chargeCostPackageId
     * @return
     */
    Result removeChargeCostPackage(String chargeCostPackageId);

    /**
     * 获取按量充值列表 前4
     *
     * @return
     */
    List<ChargeCostPackage> getChargeCostPackage();

    /**
     * 根据 电功率 获取合适配置
     *
     * @param power
     * @return
     */
    ChargeCostPackage getChargeCostPackageByPow(String power);


    /**
     * 获取 电站 关联 套餐 数量
     *
     * @param chargeCostPackageId
     * @return
     */
    int getStationUserPackage(String chargeCostPackageId);
}
