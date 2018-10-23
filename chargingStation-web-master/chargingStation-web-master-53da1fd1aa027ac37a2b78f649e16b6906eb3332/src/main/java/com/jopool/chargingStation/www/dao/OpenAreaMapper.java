package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.OpenArea;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(OpenArea record);

    int insertSelective(OpenArea record);

    OpenArea selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OpenArea record);

    int updateByPrimaryKey(OpenArea record);

    /**
     * 获取开通城市
     * @return
     */
    List<OpenArea> selectAll();

    /**
     * 分页 列表
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<OpenArea> selectBaseVo(SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 城市 查找
     * @param areaId
     * @return
     */
    OpenArea selectAreaId(@Param("areaId")String areaId);
}