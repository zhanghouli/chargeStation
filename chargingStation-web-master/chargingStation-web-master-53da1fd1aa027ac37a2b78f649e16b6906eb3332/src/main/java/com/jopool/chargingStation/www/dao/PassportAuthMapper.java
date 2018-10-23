package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.PassportAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportAuthMapper {
    int deleteByPrimaryKey(String id);

    int insert(PassportAuth record);

    int insertSelective(PassportAuth record);

    PassportAuth selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PassportAuth record);

    int updateByPrimaryKey(PassportAuth record);

    /**
     * 查询 所有
     *
     * @param passportId
     * @return
     */
    List<PassportAuth> selectByPassportId(@Param("passportId") String passportId);

    /**
     * 删除
     *
     * @param passportId
     * @return
     */
    int deletedByPassportId(String passportId);
}