package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxConfig record);

    int insertSelective(WxConfig record);

    WxConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxConfig record);

    int updateByPrimaryKey(WxConfig record);

    /**
     * 条件查找
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<WxConfig> searchByCondition(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 根据门店id和平台查找
     *
     * @param platformId
     * @param platform
     * @return
     */
    WxConfig selectByPlatformIdAndPlatForm(@Param("platformId") String platformId, @Param("platform") String platform);
}