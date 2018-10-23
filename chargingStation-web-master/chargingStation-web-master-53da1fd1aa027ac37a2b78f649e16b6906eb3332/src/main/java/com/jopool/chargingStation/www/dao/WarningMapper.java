package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Warning;
import com.jopool.chargingStation.www.vo.WarningVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchStationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningMapper {
    int deleteByPrimaryKey(String id);

    int insert(Warning record);

    int insertSelective(Warning record);

    Warning selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Warning record);

    int updateByPrimaryKey(Warning record);

    /**
     * 获取警告
     *
     * @param type
     * @param page
     * @return
     */
    List<Warning> search(@Param("reqType") int reqType, @Param("type") int type, @Param("type1") int type1, @Param("type4") int type4, RowBounds page);

    /**
     *
     * @param reqType
     * @param type
     * @param type1
     * @param type4
     * @param searchStationVo
     * @param page
     * @return
     */
    List<WarningVo> searchWarningKeyword(@Param("reqType") int reqType, @Param("type") int type, @Param("type1") int type1, @Param("type4") int type4, @Param("searchStationVo") SearchStationVo searchStationVo, @Param("dateParam")DateParam dateParam, RowBounds page);
}