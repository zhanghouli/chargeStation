package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Government;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Government record);

    int insertSelective(Government record);

    Government selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Government record);

    int updateByPrimaryKey(Government record);

    /**
     * passport 查找 政府
     * @param passportId
     * @return
     */
    Government selectPassportId(@Param("passportId")String passportId);
}