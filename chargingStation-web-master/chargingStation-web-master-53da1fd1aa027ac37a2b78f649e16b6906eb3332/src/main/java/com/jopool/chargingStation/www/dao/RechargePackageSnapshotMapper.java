package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.RechargePackageSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargePackageSnapshotMapper {
    int deleteByPrimaryKey(String id);

    int insert(RechargePackageSnapshot record);

    int insertSelective(RechargePackageSnapshot record);

    RechargePackageSnapshot selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RechargePackageSnapshot record);

    int updateByPrimaryKey(RechargePackageSnapshot record);
}