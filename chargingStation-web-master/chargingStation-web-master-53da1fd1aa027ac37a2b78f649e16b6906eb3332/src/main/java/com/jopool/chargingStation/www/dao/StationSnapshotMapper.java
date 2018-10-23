package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public interface StationSnapshotMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationSnapshot record);

    int insertSelective(StationSnapshot record);

    StationSnapshot selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationSnapshot record);

    int updateByPrimaryKey(StationSnapshot record);
}