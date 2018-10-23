package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationPortSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public interface StationPortSnapshotMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationPortSnapshot record);

    int insertSelective(StationPortSnapshot record);

    StationPortSnapshot selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationPortSnapshot record);

    int updateByPrimaryKey(StationPortSnapshot record);
}