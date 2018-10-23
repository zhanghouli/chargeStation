package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.AliConfig;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AliConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(AliConfig record);

    int insertSelective(AliConfig record);

    AliConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AliConfig record);

    int updateByPrimaryKey(AliConfig record);

    /**
     * search
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<AliConfig> searchByCondition(SearchBaseVo searchBaseVo, Pagination page);

    /**
     * select by creation_time desc
     *
     * @return
     */
    AliConfig selectByCreationTimeDesc();
}