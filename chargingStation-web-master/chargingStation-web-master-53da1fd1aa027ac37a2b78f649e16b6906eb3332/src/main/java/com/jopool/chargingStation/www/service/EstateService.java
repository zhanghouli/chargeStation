package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.vo.EstateVo;
import com.jopool.chargingStation.www.vo.common.SearchEstateVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by synn on 2017/8/28.
 */
public interface EstateService {

    /**
     * 添加 物业
     *
     * @param estate
     */
    void addEstate(Estate estate);

    /**
     * 修改 物业
     *
     * @param estate
     * @return
     */
    Result modifyEstate(Estate estate);

    /**
     * id 查找
     *
     * @param estateId
     * @return
     */
    Estate searchEstateById(String estateId);

    /**
     * 物业 手机号 重复判断
     *
     * @param phone
     * @return
     */
    Estate getByPhone(String phone);

    /**
     * Id  删除物业
     *
     * @param passportId
     * @return
     */
    Result removeEstateById(String passportId);

    /**
     * 分页 查询
     *
     * @param searchEstateVo
     * @param page
     * @return
     */
    List<EstateVo> searchEstateVo(SearchEstateVo searchEstateVo, RowBounds page);

    /**
     * 状态 修改
     *
     * @param estateId
     * @return
     */
    Result modifyStatus(String estateId);

    /**
     * passportId  获取物业
     *
     * @param passportId
     * @return
     */
    Estate getByPassportId(String passportId);

    /**
     * id 获取物业
     *
     * @param id
     * @return
     */
    Estate getById(String id);
}
