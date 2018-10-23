package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.StationFault;
import com.jopool.chargingStation.www.vo.common.SearchStationFaultVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationFaultMapper {
    int deleteByPrimaryKey(String id);

    int insert(StationFault record);

    int insertSelective(StationFault record);

    StationFault selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StationFault record);

    int updateByPrimaryKeyWithBLOBs(StationFault record);

    int updateByPrimaryKey(StationFault record);


    /**
     * 故障 电站 分页 查询
     * @param searchStationFaultVo
     * @param page
     * @return
     */
    List<StationFault> selectStationFaultVo(SearchStationFaultVo searchStationFaultVo, RowBounds page);
}