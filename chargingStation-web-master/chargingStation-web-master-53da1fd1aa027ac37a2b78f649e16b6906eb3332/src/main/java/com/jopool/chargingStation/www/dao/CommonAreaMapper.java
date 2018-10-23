package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonArea record);

    int insertSelective(CommonArea record);

    CommonArea selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonArea record);

    int updateByPrimaryKey(CommonArea record);


    /**
     * code  查找
     * @param code
     * @return
     */
    CommonArea selectByCommonAreaCode(@Param("code") String code);

    /**
     * 获取所有地区
     * @return
     */
    List<CommonArea> selectAreaAll();
}