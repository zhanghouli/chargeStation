package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.CommonSystemConfig;
import com.jopool.chargingStation.www.base.entity.OpenArea;
import com.jopool.chargingStation.www.vo.OpenAreaVo;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by synn on 2017/8/31.
 */
public interface SystemConfigService {

    /**
     * 分页 查询
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CommonSystemConfig> searchBaseVo(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * id  查找
     *
     * @param commonSystemConfigId
     * @return
     */
    CommonSystemConfig getById(String commonSystemConfigId);

    /**
     * 系统 参数 添加
     *
     * @param commonSystemConfig
     */
    void addCommonSystemConfig(CommonSystemConfig commonSystemConfig);

    /**
     * 系统 参数 修改
     *
     * @param commonSystemConfig
     * @return
     */
    Result modifyCommonSystemConfig(CommonSystemConfig commonSystemConfig);

    /**
     * id 删除
     *
     * @param commonSystemConfigId
     * @return
     */
    Result removeSystemConfigId(String commonSystemConfigId);

    /**
     * 根据name查询
     *
     * @param name
     * @return
     */
    CommonSystemConfig getConfigByName(String name);

    /**
     * 添加
     *
     * @param openArea
     */
    void addOpenArea(OpenArea openArea);

    /**
     * 修改
     *
     * @param openArea
     * @return
     */
    Result modifyArea(OpenArea openArea);

    /**
     * 查找 单条 数据
     *
     * @param openAreaId
     * @return
     */
    OpenArea getByOpenAreaId(String openAreaId);

    /**
     * 城市 列表 分页 查询
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<OpenAreaVo> searchBaseVoOpenArea(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * id 删除
     *
     * @param openAreaId
     * @return
     */
    Result removeOpenArea(String openAreaId);
}
