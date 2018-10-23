package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonBaseAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommonBaseAuthMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonBaseAuth record);

    int insertSelective(CommonBaseAuth record);

    CommonBaseAuth selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonBaseAuth record);

    int updateByPrimaryKey(CommonBaseAuth record);

    List<CommonBaseAuth> selectPassportType(@Param("type")String type);

    List<CommonBaseAuth> selectParentId(@Param("parentId")String parentId);
}