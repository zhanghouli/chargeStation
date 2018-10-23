package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);

    /**
     * os获取
     *
     * @param os
     * @return
     */
    AppVersion selectByAppIdAndOS(@Param("appId") String appId, @Param("os") String os);

    /**
     * select all
     *
     * @param page
     * @return
     */
    List<AppVersion> selectAll(RowBounds page);
}