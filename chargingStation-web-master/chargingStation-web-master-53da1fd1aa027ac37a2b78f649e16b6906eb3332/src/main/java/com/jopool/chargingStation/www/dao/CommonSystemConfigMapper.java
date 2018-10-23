package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonSystemConfig;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonSystemConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonSystemConfig record);

    int insertSelective(CommonSystemConfig record);

    CommonSystemConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonSystemConfig record);

    int updateByPrimaryKey(CommonSystemConfig record);


    /**
     * 分页 查询
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CommonSystemConfig> selectSearchBaseVo(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 根据名字查询
     *
     * @param name
     * @return
     */
    CommonSystemConfig selectByName(String name);
}