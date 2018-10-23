package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonTokenAuthAider;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonTokenAuthAiderMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonTokenAuthAider record);

    int insertSelective(CommonTokenAuthAider record);

    CommonTokenAuthAider selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonTokenAuthAider record);

    int updateByPrimaryKey(CommonTokenAuthAider record);

    /**
     * 根据手机号获取最近一条记录
     *
     * @param phone
     * @return
     */
    CommonTokenAuthAider selectLastByPhone(String phone);
}