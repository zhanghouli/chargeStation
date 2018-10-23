package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Fence;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FenceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Fence record);

    int insertSelective(Fence record);

    Fence selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Fence record);

    int updateByPrimaryKeyWithBLOBs(Fence record);

    int updateByPrimaryKey(Fence record);

    /**
     * 政府 电子围栏
     * @param fenceId
     * @param name
     * @param governmentId
     * @return
     */
    List<Fence> selectFenceList(@Param("fenceId") String fenceId, @Param("name") String name, @Param("governmentId") String governmentId);
}