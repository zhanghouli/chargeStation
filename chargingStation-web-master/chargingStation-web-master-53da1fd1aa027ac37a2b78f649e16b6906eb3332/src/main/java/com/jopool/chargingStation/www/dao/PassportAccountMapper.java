package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.PassportAccount;
import com.jopool.chargingStation.www.vo.common.DateParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportAccountMapper {
    int deleteByPrimaryKey(String passportId);

    int insert(PassportAccount record);

    int insertSelective(PassportAccount record);

    PassportAccount selectByPrimaryKey(String passportId);

    int updateByPrimaryKeySelective(PassportAccount record);

    int updateByPrimaryKey(PassportAccount record);

}