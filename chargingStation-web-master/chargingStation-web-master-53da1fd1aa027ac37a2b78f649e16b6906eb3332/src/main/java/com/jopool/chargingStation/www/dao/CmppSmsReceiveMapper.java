package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CmppSmsReceive;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmppSmsReceiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmppSmsReceive record);

    int insertSelective(CmppSmsReceive record);

    CmppSmsReceive selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmppSmsReceive record);

    int updateByPrimaryKey(CmppSmsReceive record);

    /**
     * select by searchBaseVo
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CmppSmsReceive> selectBySearchBaseVo(@Param("searchBaseVo") SearchBaseVo searchBaseVo, RowBounds page);
}